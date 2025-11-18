package io.github.luyang.starter.security.remote.dto;


import io.github.luyang.starter.security.common.enums.PrincipalType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 令牌校验响应
 *
 * @param userId        用户ID
 * @param clientId      客户端ID
 * @param displayName   显示名称 用户姓名/客户端名称
 * @param valid         令牌状态（true=有效，false=无效）
 * @param principalType 身份类型
 * @param scopes        授权范围 （如 "read", "write"，表示本次请求被允许的粗粒度范围）
 * @param authorities   功能权限集合
 * @param expiresAt     令牌过期时间戳（单位：秒）
 * @param attributes    扩展属性
 */
public record TokenValidationResponse(
	String userId,
	String clientId,
	String displayName,
	boolean valid,
	PrincipalType principalType,
	Set<String> scopes,
	Set<String> authorities,
	Long expiresAt,
	Map<String, Object> attributes
) implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
}
