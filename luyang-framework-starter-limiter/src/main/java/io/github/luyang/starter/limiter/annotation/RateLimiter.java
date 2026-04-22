package io.github.luyang.starter.limiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yang.lu
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {
	String key() default "default_limit:"; // 基础Key
	long time() default 60;                // 时间周期（秒）
	long count() default 10;               // 限制次数
	String algorithm() default "SLIDING_WINDOW"; // 算法标识
}
