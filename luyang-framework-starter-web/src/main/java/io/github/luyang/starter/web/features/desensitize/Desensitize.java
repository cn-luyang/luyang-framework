package io.github.luyang.starter.web.features.desensitize;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段脱敏注解
 *
 * @author yang.lu
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizeSerializer.class)
public @interface Desensitize {

	/**
	 * 脱敏规则
	 * 如DesensitizeRule不满足，则使用front和end
	 *
	 * @author yang.lu
	 */
	DesensitizeRule rule() default DesensitizeRule.CUSTOMIZE;

	/**
	 * 保留：前面的front位数；从1开始
	 *
	 * @author yang.lu
	 */
	int front() default 0;

	/**
	 * 保留：后面的end位数；从1开始
	 *
	 * @author yang.lu
	 */
	int end() default 0;
}
