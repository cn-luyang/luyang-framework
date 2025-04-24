package com.luyang.framework.base.wrap.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码
 *
 * @author yang.lu
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultEnum implements BaseEnum<String>{

	SUCCESS("0", "操作成功"),
	FAILURE("500", "服务器内部错误");

	private final String code;
	private final String message;
}
