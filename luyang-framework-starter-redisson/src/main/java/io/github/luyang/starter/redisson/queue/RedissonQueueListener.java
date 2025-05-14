package io.github.luyang.starter.redisson.queue;

/**
 * 基于 Redisson 客户端队列监听器
 *
 * @author yang.lu
 */
public interface RedissonQueueListener<T> {

	/**
	 * 消费队列消息
	 *
	 * @param content 消息内容
	 * @author yang.lu
	 */
	void consume(T content);
}
