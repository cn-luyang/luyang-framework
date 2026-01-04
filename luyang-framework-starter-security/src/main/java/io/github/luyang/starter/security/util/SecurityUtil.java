package io.github.luyang.starter.security.util;

import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.security.common.constant.SecurityConstant;
import io.github.luyang.starter.security.support.identity.ClientIdentity;
import io.github.luyang.starter.security.support.identity.Identity;
import io.github.luyang.starter.security.support.identity.UserIdentity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.security.access.AccessDeniedException;
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
	public static Identity getIdentity() {
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof Identity identity) {
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
	public static Optional<Identity> getIdentityOpt() {
		return Optional.ofNullable(getIdentity());
	}

	/**
	 * 获取指定类型的身份对象
	 *
	 * @param clazz 身份类型，如 UserIdentity.class
	 * @return 对应类型的身份 Optional，如果类型不匹配则为 empty
	 * @author yang.lu
	 */
	public static <T extends Identity> Optional<T> getIdentity(Class<T> clazz) {
		Identity identity = getIdentity();
		if (clazz.isInstance(identity)) {
			return Optional.of(clazz.cast(identity));
		}
		return Optional.empty();
	}

	/**
	 * 判断当前是否为用户身份
	 *
	 * @return 如果是用户身份返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isUser() {
		return getIdentity() instanceof UserIdentity;
	}

	/**
	 * 获取当前用户身份
	 *
	 * @return 当前用户身份的 Optional
	 * @author yang.lu
	 */
	public static Optional<UserIdentity> getUser() {
		return getIdentity(UserIdentity.class);
	}

	/**
	 * 获取当前用户身份（必须为用户身份，否则抛出异常）
	 *
	 * @return 当前用户身份对象
	 * @author yang.lu
	 */
	public static UserIdentity getRequiredUser() {
		return getUser().orElseThrow(() ->
			new AccessDeniedException("当前操作需要用户身份登录"));
	}

	/**
	 * 获取当前用户 ID
	 *
	 * @return 用户 ID
	 * @author yang.lu
	 */
	public static String getCurrentUserId() {
		return getUser().map(UserIdentity::getUserId).orElse(null);
	}

	/**
	 * 获取当前用户名
	 *
	 * @return 用户中文名
	 * @author yang.lu
	 */
	public static String getCurrentUsername() {
		return getUser().map(UserIdentity::getZhName).orElse(null);
	}

	/**
	 * 判断当前是否为客户端身份
	 *
	 * @return 如果是客户端身份返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isClient() {
		return getIdentity() instanceof ClientIdentity;
	}

	/**
	 * 获取当前客户端身份
	 *
	 * @return 当前客户端身份的 Optional，如果非客户端身份则为 empty
	 * @author yang.lu
	 */
	public static Optional<ClientIdentity> getClient() {
		return getIdentity(ClientIdentity.class);
	}

	/**
	 * 获取当前客户端 ID
	 *
	 * @return 客户端 ID，如果未认证则返回 null
	 * @author yang.lu
	 */
	public static String getCurrentClientId() {
		Identity identity = getIdentity();
		// 利用 Java 16+ 模式匹配
		if (identity instanceof ClientIdentity client) {
			return client.getClientId();
		} else if (identity instanceof UserIdentity user) {
			return user.getClientId();
		}
		return null;
	}
}
