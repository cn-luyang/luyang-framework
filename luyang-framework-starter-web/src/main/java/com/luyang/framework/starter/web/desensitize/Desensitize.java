package com.luyang.framework.starter.web.desensitize;


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
	 * 脱敏规则枚举类
	 *
	 * @author yang.lu
	 */
	Class<? extends Enum<? extends DesensitizeRule>> enumClass();

	/**
	 * 使用的具体脱敏规则名（枚举值）
	 *
	 * @author yang.lu
	 */
	String enumName();
}
