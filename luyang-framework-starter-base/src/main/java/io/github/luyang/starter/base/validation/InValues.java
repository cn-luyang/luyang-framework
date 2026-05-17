package io.github.luyang.starter.base.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 固定字符串值包含校验注解
 * 用于验证属性值是否在给定的字符串明细列表中，@InValues(values = {"PC", "APP", "MINI"}, message = "终端类型不正确")
 *
 * @author yang.lu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InValuesValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface InValues {

	/**
	 * 校验失败时的提示消息
	 */
	String message() default "{validation.base.inValues}";

	/**
	 * 允许的固定字符串值列表
	 */
	String[] values() default {};

	/**
	 * 是否允许空值（true 允许，默认 false）
	 */
	boolean allowEmpty() default false;

	/**
	 * 校验分组
	 */
	Class<?>[] groups() default {};

	/**
	 * 负载元数据，供框架使用
	 */
	Class<? extends Payload>[] payload() default {};
}
