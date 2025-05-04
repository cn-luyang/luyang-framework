package com.luyang.framework.starter.redisson.queue;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import org.redisson.RedissonShutdownException;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Redisson 队列监听初始化器
 *
 * @author yang.lu
 */
public class RedissonQueueInitialize implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(RedissonQueueInitialize.class);

	/** 队列前缀 */
	private static final String QUEUE_PREFIX = "REDISSON-QUEUE";
	/** 线程池，异步执行队列的消息消费任务 */
	private ExecutorService executorService;
	/** 标记系统是否请求关闭，用于控制队列监听的停止 */
	private final AtomicBoolean shutdownRequested = new AtomicBoolean(false);

	private final RedissonClient redissonClient;

	public RedissonQueueInitialize(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	/**
	 * 构建带有前缀的完整队列名称
	 *
	 * @param queueName 队列的基础名称
	 * @return String 带有前缀的完整队列名称
	 */
	public static String buildQueueName(String queueName) {
		return QUEUE_PREFIX + StrPool.COLON + queueName;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// 获取所有 RedissonQueueListener 类型的 Bean
		var beanMap = applicationContext.getBeansOfType(RedissonQueueListener.class);
		if (MapUtil.isEmpty(beanMap)) {
			logger.warn("没有找到任何 RedissonQueueListener 实现。");
			return;
		}

		// 初始化线程池，处理队列监听任务
		executorService = createThreadPool();

		// 遍历所有 RedissonQueueListener 类型的 Bean
		beanMap.forEach((beanName, listener) ->
			Optional.ofNullable(listener)
				.map(Object::getClass)
				.map(clazz -> clazz.getAnnotation(RedissonQueue.class))
				.ifPresent(annotation -> {
					@SuppressWarnings("unchecked")
					RedissonQueueListener<Object> castedListener = (RedissonQueueListener<Object>) listener;
					// 异步执行队列监听任务
					executorService.submit(() -> listenToQueue(annotation.value(), castedListener));
				}));
	}

	/**
	 * 监听指定的队列，异步消费队列中取出的消息
	 *
	 * @param queueName 队列名称
	 * @param listener  队列监听器
	 * @author yang.lu
	 */
	private <T> void listenToQueue(String queueName, RedissonQueueListener<T> listener) {
		// 构建完整的队列名称
		queueName = buildQueueName(queueName);
		var blockingQueue = redissonClient.<T>getBlockingQueue(queueName);
		while (!shutdownRequested.get() && !redissonClient.isShutdown()) {
			try {
				// 从队列中取出消息（阻塞操作）
				T message = blockingQueue.take();
				// 将消息的消费任务提交到线程池中异步执行
				executorService.submit(() -> listener.consume(message));
			} catch (RedissonShutdownException e) {
				logger.info("Redisson 连接关闭，停止监听队列:{}", queueName);
				break;
			} catch (Exception e) {
				logger.error("监听队列异常:{}", queueName, e);
			}
		}
	}

	/**
	 * 自定义 Redisson 队列线程池
	 *
	 * @return ExecutorService 线程池
	 * @author yang.lu
	 */
	private ExecutorService createThreadPool() {
		return new ThreadPoolExecutor(
			// 核心线程数：设置为可用处理器数的两倍，在多核处理器上能够充分利用资源
			Runtime.getRuntime().availableProcessors() * 2,
			// 最大线程数：设置为可用处理器数的四倍，允许在高负载情况下增加处理能力
			Runtime.getRuntime().availableProcessors() * 4,
			// 线程空闲时间：当线程空闲时，最多保持 60 秒后将其终止
			60L, TimeUnit.SECONDS,
			// 工作队列：使用一个容量为 100 的 LinkedBlockingQueue 来存储待处理的任务
			new LinkedBlockingQueue<>(100),
			// 自定义线程工厂：使用 NamedThreadFactory 创建线程
			new NamedThreadFactory(QUEUE_PREFIX),
			// 拒绝策略：使用 CallerRunsPolicy，当线程池无法处理请求时，调用者线程将执行该任务
			new ThreadPoolExecutor.CallerRunsPolicy()
		);
	}

	/**
	 * 自定义线程工厂，为线程池中的每个线程命名
	 *
	 * @author yang.lu
	 */
	private record NamedThreadFactory(String namePrefix) implements ThreadFactory {

		// 线程编号
		private static final AtomicInteger threadNumber = new AtomicInteger(1);

		@Override
		@SuppressWarnings("all")
		public Thread newThread(Runnable runnable) {
			// 使用虚拟线程，为线程命名
			return Thread.ofVirtual()
				.name(namePrefix + StrPool.DASHED + threadNumber.getAndIncrement())
				.unstarted(runnable);
		}
	}
}
