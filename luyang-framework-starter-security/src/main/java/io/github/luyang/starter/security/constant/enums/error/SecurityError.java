package io.github.luyang.starter.security.constant.enums.error;

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

	VALIDATE_EXCEPTION_ACCESS_TOKEN("validate_exception_access_token", "访问令牌验证异常"),
	MISSING_ACCESS_TOKEN("missing_access_token", "缺失访问令牌"),
	EXPIRED_ACCESS_TOKEN("expired_access_token", "访问令牌已过期"),
	PERMISSION_DENIED("permission_denied", "权限不足"),
	;

	private final String code;
	private final String message;
}
