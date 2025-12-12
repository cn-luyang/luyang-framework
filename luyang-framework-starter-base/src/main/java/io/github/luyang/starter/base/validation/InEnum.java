package io.github.luyang.starter.base.validation;

import io.github.luyang.starter.base.enums.IBaseEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 有效枚举值校验注解
 *
 * @author yang.lu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InEnumValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface InEnum {

	/**
	 * 校验失败时的提示消息
	 */
	String message() default "{validation.base.isEnum}";

	/**
	 * 指定目标枚举类型，必须实现 {@link IBaseEnum} 接口
	 */
	Class<? extends IBaseEnum<?>> value();

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
