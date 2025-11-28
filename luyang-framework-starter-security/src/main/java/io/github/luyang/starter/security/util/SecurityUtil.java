package io.github.luyang.starter.security.util;

import io.github.luyang.base.util.StrUtil;
import io.github.luyang.starter.security.common.constant.SecurityConstant;
import io.github.luyang.starter.security.support.identity.AuthSubject;
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
	 * 从当前请求中获取令牌
	 *
	 * @param request 当前请求
	 * @return 令牌
	 * @author yang.lu
	 */
	public static String extractToken(HttpServletRequest request) {

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
	 * 获取当前认证主体
	 *
	 * @author yang.lu
	 */
	public static AuthSubject getSubject() {
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof AuthSubject subject) {
			return subject;
		}
		return null;
	}

	/**
	 * 获取当前用户ID
	 * 只有当主体确实是 USER 类型时才返回，否则返回 null
	 *
	 * @author yang.lu
	 */
	public static String getCurrentUserId() {
		AuthSubject subject = getSubject();
		if (subject != null && subject.isUser()) {
			return subject.getId();
		}
		return null;
	}

	/**
	 * 获取当前客户端ID
	 *
	 * @author yang.lu
	 */
	public static String getCurrentClientId() {
		AuthSubject subject = getSubject();
		return subject != null ? subject.clientId() : null;
	}

	/**
	 * 检查当前请求是否拥有某个 Scope
	 *
	 * @author yang.lu
	 */
	public static boolean hasScope(String scope) {
		AuthSubject subject = getSubject();
		return subject != null && subject.scopes().contains(scope);
	}
}
