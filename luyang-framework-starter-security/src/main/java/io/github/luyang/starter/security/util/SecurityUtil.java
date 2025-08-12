package io.github.luyang.starter.security.util;

import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.security.UnifiedPrincipal;
import io.github.luyang.starter.security.constant.SecurityConstant;
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
	 * 从 Spring Security 上下文中获取当前登录用户的 UnifiedPrincipal 实例
	 *
	 * @return 如果认证主体是 UnifiedPrincipal 类型，则返回该对象；否则返回 null
	 * @author yang.lu
	 */
	public static UnifiedPrincipal getUnifiedPrincipal() {
		return getUnifiedPrincipalOpt().orElse(null);
	}

	public static Optional<UnifiedPrincipal> getUnifiedPrincipalOpt() {
		return Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
			.map(Authentication::getPrincipal)
			.filter(UnifiedPrincipal.class::isInstance)
			.map(UnifiedPrincipal.class::cast);
	}

	public static Long getUserId() {
		return getUnifiedPrincipalOpt()
			.map(UnifiedPrincipal::userId)
			.orElse(null);
	}

	public static Long getClientId() {
		return getUnifiedPrincipalOpt()
			.map(UnifiedPrincipal::clientId)
			.orElse(null);
	}
}
