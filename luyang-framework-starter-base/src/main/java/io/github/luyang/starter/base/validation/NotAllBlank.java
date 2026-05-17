package io.github.luyang.starter.base.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多字段联合非空校验注解
 * 用于要求指定的多个属性中，至少有一个属性具有有效值（非 Null 且非空字符串）
 *
 * @author yang.lu
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotAllBlankValidator.class)
public @interface NotAllBlank {

	/**
	 * 校验失败时的提示消息
	 */
	String message() default "{validation.base.notAllBlank}";

	/**
	 * 校验分组
	 */
	Class<?>[] groups() default {};

	/**
	 * 负载元数据
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * 需要校验的字段名
	 */
	String[] fields();
}
