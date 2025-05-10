package com.luyang.framework.starter.security.constant;

import com.luyang.framework.starter.base.enums.IBaseEnum;
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
public enum SecurityErrorEnum implements IBaseEnum<String> {

	/**
	 * 用户未登录或凭证已过期时返回
	 */
	UNAUTHORIZED("401", "未登录或登录超时"),
	/**
	 * 用户无权限访问资源时返回
	 */
	FORBIDDEN("403", "权限不足或请求被拒绝"),
	;
	private final String code;
	private final String message;
}
