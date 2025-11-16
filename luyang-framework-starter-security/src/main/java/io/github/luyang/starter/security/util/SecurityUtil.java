package io.github.luyang.starter.security.util;

import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.security.AuthUser;
import io.github.luyang.starter.security.common.constant.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Security 相关工具类
 *
 * @author yang.lu
 */
@UtilityClass
public class SecurityUtil {

	/**
	 * 从当前请求中获取认证令牌
	 *
	 * @param request 当前请求
	 * @return 令牌
	 * @author yang.lu
	 */
	public static String getAccessTokenValue(HttpServletRequest request) {

		String token = StrUtil.blankToDefault(
			request.getHeader(SecurityConstant.X_ACCESS_TOKEN),
			request.getParameter(SecurityConstant.X_ACCESS_TOKEN)
		);

		if (StrUtil.isBlank(token)) {
			return null;
		}

		return StrUtil.removePrefix(token, "Bearer ");
	}

	/**
	 * 获取当前上下文中的认证信息
	 *
	 * @return 当前认证信息，为空则返回 null
	 * @author yang.lu
	 */
	public static Authentication getAuthentication() {
		return Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
			.orElse(null);
	}

	/**
	 * 获取当前已认证的用户信息
	 *
	 * @return 当前认证用户，未认证或类型不匹配则返回 null
	 * @author yang.lu
	 */
	public static AuthUser getCurrentUser() {
		return Optional.ofNullable(getAuthentication())
			.map(Authentication::getPrincipal)
			.filter(AuthUser.class::isInstance)
			.map(AuthUser.class::cast)
			.orElse(null);
	}

	/**
	 * 获取认证用户ID
	 *
	 * @return 当前用户ID，用户未认证则返回 null
	 * @author yang.lu
	 */
	public static String getCurrentUserId() {
		return Optional.ofNullable(getCurrentUser())
			.map(AuthUser::userId)
			.orElse(null);
	}
}
