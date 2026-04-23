package io.github.luyang.starter.security.common.enums.error;

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
public enum SecurityError implements IBaseEnum<String> {

	TOKEN_INVALID("token_invalid", "Token无效"),
	TOKEN_MISSING("token_missing", "Token缺失"),
	TOKEN_EXPIRED("token_expired", "Token已过期"),
	TOKEN_AUTHENTICATION_FAILED("authentication_failed", "Token认证失败"),
	PERMISSION_DENIED("permission_denied", "无权限访问"),
	AUTH_SERVICE_UNAVAILABLE("auth_service_unavailable", "认证中心服务忙，请稍后再试");

    private final String code;
    private final String message;
}
