package io.github.luyang.base.util.thread;

import io.github.luyang.base.util.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 全局公共线程池
 *
 * @author yang.lu
 */
public class GlobalThreadPool {

	private static final Logger logger = LoggerFactory.getLogger(GlobalThreadPool.class);

	private GlobalThreadPool() {
	}

	private static final int CPU = Runtime.getRuntime().availableProcessors();

	static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
		// 核心线程数：CPU × 2 + 1
		CPU * 2 + 1,
		// 最大线程数
		CPU * 4,
		// 非核心线程30秒后回收
		30L, TimeUnit.SECONDS,
		// 大队列：1024，既防拒绝又防OOM
		new LinkedBlockingQueue<>(1024),
		// 自定义线程工厂（带命名 + 异常捕获）
		new NamedThreadFactory("Global-", false),
		// 最强拒绝策略：CallerRuns（自动限流，不丢任务）
		new ThreadPoolExecutor.CallerRunsPolicy()
	);

	static {
		// 允许核心线程也超时回收（低峰期内存归零）
		EXECUTOR.allowCoreThreadTimeOut(true);
		// 预启动所有核心线程，首次任务零延迟
		EXECUTOR.prestartAllCoreThreads();
		// JVM关闭时自动优雅关闭线程池
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.info("正在关闭全局线程池...");
			EXECUTOR.shutdown();
			try {
				if (!EXECUTOR.awaitTermination(10, TimeUnit.SECONDS)) {
					logger.info("线程池未完全关闭，强制终止");
					EXECUTOR.shutdownNow();
				}
			} catch (InterruptedException e) {
				EXECUTOR.shutdownNow();
				Thread.currentThread().interrupt();
			}
		}));
	}

	/**
	 * 在全局线程池中执行任务
	 *
	 * @param runnable 要执行的任务
	 * @author yang.lu
	 */
	public static void execute(Runnable runnable) {
		try {
			EXECUTOR.execute(runnable);
		} catch (Exception e) {
			throw new UtilException(e, "执行任务时发生异常");
		}
	}

	/**
	 * 执行有返回值的异步方法。
	 * <p>
	 * Future 代表一个异步执行的操作，通过 get() 方法可以获得操作的结果，如果异步操作还没有完成，则 get() 会使当前线程阻塞。
	 *
	 * @param <T>  返回值类型
	 * @param task Callable 任务
	 * @return Future
	 * @author yang.lu
	 */
	public static <T> Future<T> submit(Callable<T> task) {
		return EXECUTOR.submit(task);
	}

	/**
	 * 执行无返回值的异步方法。
	 *
	 * @param runnable Runnable 任务
	 * @return Future
	 * @author yang.lu
	 */
	public static Future<?> submit(Runnable runnable) {
		return EXECUTOR.submit(runnable);
	}

	/**
	 * 检查线程池是否已关闭
	 *
	 * @return 如果已关闭返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isShutdown() {
		return EXECUTOR.isShutdown();
	}

	/**
	 * 关闭公共线程池
	 *
	 * @param immediate 是否立即关闭而不等待正在执行的线程
	 * @author yang.lu
	 */
	public static synchronized void shutdown(boolean immediate) {
		if (immediate) {
			EXECUTOR.shutdownNow();
		} else {
			EXECUTOR.shutdown();
		}
	}
}
