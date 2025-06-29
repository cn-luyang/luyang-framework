package io.github.luyang.starter.web.constant.error;

import io.github.luyang.starter.base.enums.IBaseEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Web 相关错误枚举
 *
 * @author yang.lu
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum WebError implements IBaseEnum<String> {

	BAD_REQUEST("400", "请求参数错误"),
	NOT_FOUND("404", "资源不存在"),
	METHOD_NOT_ALLOWED("405", "方法不被允许"),
	;
	private final String code;
	private final String message;
}
