package io.github.luyang.starter.redisson.queue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Redisson 队列注解
 *
 * @author yang.lu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonQueue {

	/**
	 * 设置队列名
	 */
	String value();
}
