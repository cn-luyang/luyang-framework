package io.github.luyang.starter.security.common.enums;

import io.github.luyang.starter.base.enums.IBaseEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Security 相关错误枚举
 *
 * @author yang.lu
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SecurityErrorCode implements IBaseEnum<String> {

	TOKEN_MISSING("40100", "Token缺失"),
	TOKEN_INVALID("40101", "Token无效"),
	TOKEN_EXPIRED("40102", "Token已过期"),
	TOKEN_AUTHENTICATION_FAILED("40103", "Token认证失败"),

	PERMISSION_DENIED("40300", "无权限访问");

    private final String code;
    private final String message;
}
