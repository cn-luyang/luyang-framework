package io.github.luyang.starter.security.util;

import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.security.UserIdentity;
import io.github.luyang.starter.security.common.constant.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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

		return StrUtil.isNotBlank(token) ? StrUtil.removePrefix(token, "Bearer ") : null;
	}

	/**
	 * 获取当前认证信息
	 *
	 * @return 当前认证信息，如果未认证则返回 null
	 * @author yang.lu
	 */
	public static Authentication getAuthentication() {
		return Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
			.orElse(null);
	}

	/**
	 * 判断当前是否已认证（非匿名用户）
	 *
	 * @return 如果已认证且非匿名用户返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isAuthenticated() {
		Authentication auth = getAuthentication();
		return auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
	}

	/**
	 * 获取当前身份对象
	 *
	 * @return 当前身份对象，如果未认证或身份类型不匹配则返回 null
	 * @author yang.lu
	 */
	public static UserIdentity getUserIdentity() {
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserIdentity identity) {
			return identity;
		}
		return null;
	}

	/**
	 * 获取当前身份对象的 Optional 包装
	 *
	 * @return 包含当前身份对象的 Optional，如果无身份则为 empty
	 * @author yang.lu
	 */
	public static Optional<UserIdentity> getUserIdentityOpt() {
		return Optional.ofNullable(getUserIdentity());
	}

	/**
	 * 获取当前用户 ID
	 *
	 * @return 用户 ID
	 * @author yang.lu
	 */
	public static String getCurrentUserId() {
		return getUserIdentityOpt().map(UserIdentity::getUserId).orElse(null);
	}
}
