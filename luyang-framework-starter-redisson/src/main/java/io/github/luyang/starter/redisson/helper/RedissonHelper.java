package io.github.luyang.starter.redisson.helper;

import io.github.luyang.starter.redisson.queue.RedissonQueueInitialize;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBucket;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RFuture;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Redisson 助手类
 *
 * @author yang.lu
 */
public class RedissonHelper {

	private final RedissonClient redissonClient;

	public RedissonHelper(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	// ============================= String类型操作 ============================

	/**
	 * 将值存储到 Redis 中
	 *
	 * @param key   键
	 * @param value 值
	 * @author yang.lu
	 */
	public <T> void setString(String key, T value) {
		this.redissonClient.getBucket(key).set(value);
	}

	/**
	 * 将值存储到 Redis 中，并设置过期时间
	 *
	 * @param key      键
	 * @param value    值
	 * @param duration 过期时间
	 * @author yang.lu
	 */
	public <T> void setString(String key, T value, Duration duration) {
		this.redissonClient.getBucket(key).set(value, duration);
	}

	/**
	 * 设置字符串类型的值，并设置过期时间
	 *
	 * @param key      键
	 * @param value    值
	 * @param duration 过期时间
	 * @return 异步操作结果
	 * @author wangjixin
	 */
	public RFuture<Void> setStringAsync(String key, Object value, Duration duration) {
		RBucket<Object> bucket = redissonClient.getBucket(key);
		return bucket.setAsync(value, duration);
	}

	/**
	 * 根据键获取 Redis 中的值
	 *
	 * @param key 键
	 * @return T 值
	 * @author yang.lu
	 */
	public <T> T getString(String key) {
		RBucket<T> bucket = this.redissonClient.getBucket(key);
		return bucket.get();
	}

	// ============================= Hash类型操作 ============================

	/**
	 * 将值存储到 Hash 中
	 *
	 * @param key   键
	 * @param field hash键
	 * @param value 值
	 * @return boolean 是否成功
	 * @author yang.lu
	 */
	public <T> boolean addToHash(String key, Object field, T value) {
		RMap<Object, T> hash = this.redissonClient.getMap(key);
		return hash.fastPut(field, value);
	}

	/**
	 * 将值存储到 Hash 中，并设置过期时间
	 *
	 * @param key      键
	 * @param field    hash键
	 * @param value    值
	 * @param duration 过期时间
	 * @return boolean 是否成功
	 * @author yang.lu
	 */
	public <T> boolean addToHash(String key, Object field, T value, Duration duration) {
		RMap<Object, T> hash = this.redissonClient.getMap(key);
		return hash.fastPut(field, value) && hash.expire(duration);
	}

	/**
	 * 根据键和 Hash 键获取值
	 *
	 * @param key   键
	 * @param field hash键
	 * @return T 值
	 * @author yang.lu
	 */
	public <T> T getFromHash(String key, Object field) {
		RMap<Object, T> hash = this.redissonClient.getMap(key);
		return hash.get(field);
	}

	/**
	 * 获取整个 Hash
	 *
	 * @param key 键
	 * @return Map<Object, T> hash键值对
	 * @author yang.lu
	 */
	public <T> Map<Object, T> getFromHash(String key) {
		RMap<Object, T> hash = this.redissonClient.getMap(key);
		return hash.readAllMap();
	}

	/**
	 * 更新 Hash 中的值
	 *
	 * @param key   键
	 * @param field hash键
	 * @param value 值
	 * @return boolean 是否成功
	 * @author yang.lu
	 */
	public <T> boolean updateToHash(String key, Object field, T value) {
		return this.redissonClient.getMap(key).fastReplace(field, value);
	}

	/**
	 * 从 Hash 中删除指定的键
	 *
	 * @param key      键
	 * @param hashKeys hash键
	 * @return long 删除成功的数量
	 * @author yang.lu
	 */
	@SafeVarargs
	public final <T> long removeFromHash(String key, T... hashKeys) {
		return this.redissonClient.getMap(key).fastRemove(hashKeys);
	}

	// ============================= List类型操作 ============================

	/**
	 * 向 List 中添加值
	 *
	 * @param key   键
	 * @param value 值
	 * @return boolean 是否成功
	 * @author yang.lu
	 */
	public <T> boolean addToList(String key, T value) {
		return this.redissonClient.getList(key).add(value);
	}

	/**
	 * 向 List 中添加多个值
	 *
	 * @param key   键
	 * @param value 值
	 * @return boolean 是否成功
	 * @author yang.lu
	 */
	public <T> boolean addToList(String key, List<T> value) {
		RList<T> list = this.redissonClient.getList(key);
		return list.addAll(value);
	}

	/**
	 * 向 List 中添加值并设置过期时间
	 *
	 * @param key      键
	 * @param value    值
	 * @param duration 过期时间
	 * @return boolean 是否成功
	 * @author yang.lu
	 */
	public <T> boolean addToList(String key, T value, Duration duration) {
		RList<T> list = this.redissonClient.getList(key);
		list.add(value);
		return list.expire(duration);
	}

	/**
	 * 从 List 中获取指定范围的值
	 *
	 * @param key   键
	 * @param start 起始位置
	 * @param end   结束位置
	 * @return List<T> 值列表
	 * @author yang.lu
	 */
	public <T> List<T> getFromList(String key, int start, int end) {
		RList<T> list = this.redissonClient.getList(key);
		return list.range(start, end);
	}

	/**
	 * 获取 List 中的所有值
	 *
	 * @param key 键
	 * @return List<T> 值列表
	 * @author yang.lu
	 */
	public <T> List<T> getFromList(String key) {
		RList<T> list = this.redissonClient.getList(key);
		return list.readAll();
	}

	/**
	 * 移除集合左侧第一个元素
	 *
	 * @param key 键
	 * @author yang.lu
	 */
	public void removeListLeft(String key) {
		this.redissonClient.getList(key).fastRemove(0);
	}

	/**
	 * 移除集合右侧第一个元素
	 *
	 * @param key 键
	 * @author yang.lu
	 */
	public void removeListRight(String key) {
		this.redissonClient.getList(key).removeLast();
	}

	/**
	 * 移除集合指定位置的元素
	 *
	 * @param key   键
	 * @param index 索引
	 * @author yang.lu
	 */
	public void removeFromList(String key, int index) {
		this.redissonClient.getList(key).fastRemove(index);
	}

	/**
	 * 移除集合指定的元素
	 *
	 * @param key   键
	 * @param value 值
	 * @return boolean 是否成功
	 * @author yang.lu
	 */
	public <T> boolean removeFromList(String key, T value) {
		return this.redissonClient.getList(key).removeIf(o -> o.equals(value));
	}

	// ============================= ZSet类型操作 ============================

	/**
	 * 添加值到 ZSet 中
	 *
	 * @param key   键
	 * @param value 值
	 * @param score 分数
	 * @author yang.lu
	 */
	public <T> void addToZSet(String key, T value, double score) {
		this.redissonClient.getScoredSortedSet(key).add(score, value);
	}

	/**
	 * 在 ZSet 中添加值并设置过期时间
	 *
	 * @param key      键
	 * @param value    值
	 * @param score    分数
	 * @param duration 过期时间
	 * @author yang.lu
	 */
	public <T> void addToZSet(String key, T value, double score, Duration duration) {
		RScoredSortedSet<T> sortedSet = this.redissonClient.getScoredSortedSet(key);
		sortedSet.add(score, value);
		sortedSet.expire(duration);
	}

	/**
	 * 获取 ZSet 的范围元素
	 *
	 * @param key   键
	 * @param start 起始位置
	 * @param end   结束位置
	 * @return Set<Object> ZSet 中的值
	 * @author yang.lu
	 */
	public <T> Set<T> getFromZSet(String key, int start, int end) {
		RScoredSortedSet<T> sortedSet = this.redissonClient.getScoredSortedSet(key);
		return new HashSet<>(sortedSet.valueRange(start, end));
	}

	/**
	 * 从 ZSet 中删除指定的值
	 *
	 * @param key    键
	 * @param values 值列表
	 * @author yang.lu
	 */
	public <T> void removeFromZSet(String key, List<T> values) {
		this.redissonClient.getScoredSortedSet(key).removeAll(values);
	}

	/**
	 * 从 ZSet 中删除指定的值
	 *
	 * @param key   键
	 * @param value 值
	 * @author yang.lu
	 */
	public <T> void removeFromZSet(String key, T value) {
		this.redissonClient.getScoredSortedSet(key).remove(value);
	}

	// ============================= Common ============================

	/**
	 * 判断 Key 是否存在
	 *
	 * @param key 键
	 * @return boolean 存在返回 true，否则返回 false
	 * @author yang.lu
	 */
	public boolean exists(String key) {
		return this.redissonClient.getBucket(key).isExists();
	}

	/**
	 * 删除 Key
	 *
	 * @param key 键
	 * @return boolean 是否成功
	 * @author yang.lu
	 */
	public boolean remove(String key) {
		return this.redissonClient.getKeys().delete(key) > 0;
	}

	/**
	 * 设置 Key 的过期时间
	 *
	 * @param key      键
	 * @param duration 过期时间
	 * @return boolean 设置成功返回true，否则返回false
	 * @author yang.lu
	 */
	public boolean expire(String key, Duration duration) {
		return this.redissonClient.getBucket(key).expire(duration);
	}

	/**
	 * 获取 Key 的过期时间
	 *
	 * @param key 键
	 * @return Long 过期时间
	 * @author yang.lu
	 */
	public Long getExpire(String key) {
		return this.redissonClient.getBucket(key).getExpireTime();
	}

	/**
	 * 递增操作
	 *
	 * @param key 键
	 * @return long 递增后的值
	 * @author yang.lu
	 */
	public long increment(String key) {
		return this.redissonClient.getAtomicLong(key).incrementAndGet();
	}

	/**
	 * 递减操作
	 *
	 * @param key 键
	 * @return long 递减后的值
	 * @author yang.lu
	 */
	public long decrement(String key) {
		return this.redissonClient.getAtomicLong(key).decrementAndGet();
	}

	/**
	 * 发布消息到指定主题
	 *
	 * @param topic   主题名称
	 * @param message 消息内容
	 * @author yang.lu
	 */
	public <T> void publish(String topic, T message) {
		this.redissonClient.getTopic(topic).publish(message);
	}

	/**
	 * 发布消息到指定主题，并对消息执行额外的操作
	 *
	 * @param topic    主题名称
	 * @param message  消息内容
	 * @param consumer 对消息执行的操作
	 * @author yang.lu
	 */
	public <T> void publish(String topic, T message, Consumer<T> consumer) {
		publish(topic, message);
		consumer.accept(message);
	}

	/**
	 * 订阅指定主题，并指定消息类型和处理逻辑
	 *
	 * @param topic    主题名称
	 * @param clazz    消息类型
	 * @param consumer 消息处理逻辑
	 * @author yang.lu
	 */
	public <T> void subscribe(String topic, Class<T> clazz, Consumer<T> consumer) {
		this.redissonClient.getTopic(topic).addListener(clazz, (channel, msg) -> consumer.accept(msg));
	}

	/**
	 * 添加队列
	 *
	 * @param queueName 队列名称
	 * @param content   消息内容
	 * @return boolean false添加至队列失败
	 * @author yang.lu
	 */
	public <T> boolean addQueue(String queueName, T content) {
		queueName = RedissonQueueInitialize.buildQueueName(queueName);
		RBlockingQueue<T> blockingQueue = this.redissonClient.getBlockingQueue(queueName);
		return blockingQueue.add(content);
	}

	/**
	 * 添加延迟队列
	 *
	 * @param queueName 队列名称
	 * @param content   消息内容
	 * @param delay     延迟时间
	 * @param timeUnit  时间单位
	 * @author yang.lu
	 */
	public <T> void addDelayQueue(String queueName, T content, long delay, TimeUnit timeUnit) {
		queueName = RedissonQueueInitialize.buildQueueName(queueName);
		RBlockingQueue<T> blockingQueue = this.redissonClient.getBlockingQueue(queueName);
		RDelayedQueue<T> delayedQueue = this.redissonClient.getDelayedQueue(blockingQueue);
		delayedQueue.offer(content, delay, timeUnit);
	}
}
