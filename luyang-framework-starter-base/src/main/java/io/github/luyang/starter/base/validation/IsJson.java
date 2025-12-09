package io.github.luyang.starter.base.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON 格式校验注解
 *
 * @author yang.lu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsJsonValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
public @interface IsJson {

	/**
	 * 校验失败时的提示消息
	 */
	String message() default "{validation.base.isJson}";

	/**
	 * 是否允许空值或空白字符串（true 允许，默认 false）
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
