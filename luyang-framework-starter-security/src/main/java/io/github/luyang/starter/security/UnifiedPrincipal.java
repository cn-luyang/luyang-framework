package io.github.luyang.starter.security;

import io.github.luyang.starter.security.constant.enums.PrincipalType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 统一身份主体（用户或客户端）
 *
 * @param clientId      用户ID
 * @param userId        客户端ID
 * @param principalType 身份类型（用户或客户端），参考 {@link PrincipalType}
 * @param getAttributes 通用扩展字段，可附加组织机构、登录时间等上下文
 * @author yang.lu
 */
public record UnifiedPrincipal(
	String clientId,
	String userId,
	PrincipalType principalType,
	Map<String, Object> getAttributes
) implements Serializable {

	@Serial
	private static final long serialVersionUID = 4758668578675934182L;

	public boolean isUser() {
		return principalType == PrincipalType.USER;
	}

	public boolean isClient() {
		return principalType == PrincipalType.CLIENT;
	}
}
