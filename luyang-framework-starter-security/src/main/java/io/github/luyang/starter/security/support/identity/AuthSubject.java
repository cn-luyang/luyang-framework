package io.github.luyang.starter.security.support.identity;

import io.github.luyang.starter.security.common.enums.PrincipalType;
import io.github.luyang.starter.security.support.identity.principal.SecurityPrincipal;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


/**
 * 统一认证主体
 *
 * @param clientId      发起请求的客户端ID (如果是用户登录，这里记录是哪个应用发起的)
 * @param principal     核心身份（用户或客户端）
 * @param principalType 身份类型
 * @param scopes        拥有的权限范围 (e.g., "scope:read", "scope:write")
 * @param authorities   功能权限集合
 * @param attributes    扩展属性
 * @author yang.lu
 */
public record AuthSubject(
	String clientId,
	SecurityPrincipal principal,
	PrincipalType principalType,
	Set<String> scopes,
	Set<String> authorities,
	Map<String, Object> attributes
) implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 是否为用户身份
	 *
	 * @return 如果是用户身份返回true，否则返回false
	 * @author yang.lu
	 */
	public boolean isUser() {
		return principalType == PrincipalType.USER;
	}

	/**
	 * 检查是否为客户端身份
	 *
	 * @return 如果是客户端身份返回true，否则返回false
	 * @author yang.lu
	 */
	public boolean isClient() {
		return principalType == PrincipalType.CLIENT;
	}

	/**
	 * 获取身份ID
	 *
	 * @return 身份唯一标识
	 * @author yang.lu
	 */
	public String getId() {
		return principal.getId();
	}

	/**
	 * 泛型转换辅助方法，避免外部强制类型转换
	 *
	 * @author yang.lu
	 */
	public <T extends SecurityPrincipal> Optional<T> getPrincipal(Class<T> clazz) {
		if (clazz.isInstance(principal)) {
			return Optional.of(clazz.cast(principal));
		}
		return Optional.empty();
	}

	/**
	 * 获取扩展属性
	 *
	 * @param key 属性KEY
	 * @author yang.lu
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key) {
		return (T) attributes.get(key);
	}
}
