package io.github.luyang.framework.starter.web.constant;

import io.github.luyang.framework.starter.base.enums.IBaseEnum;
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
public enum WebErrorEnum implements IBaseEnum<String> {

	/** 请求参数有误时使用 */
	BAD_REQUEST("400", "参数缺失或类型错误"),

	/** 请求的资源不存在或URL错误时返回 */
	NOT_FOUND("404", "资源不存在或URL不正确"),

	/** 请求方法与资源不匹配时使用，如GET/POST错误 */
	METHOD_NOT_ALLOWED("405", "请求方法不被允许"),
	;
	private final String code;
	private final String message;
}
