package io.github.luyang.starter.security.support.identity.principal;

import java.util.Map;

/**
 * 用户身份
 *
 * @param userId     用户ID
 * @param zhName     中文名
 * @param attributes 用户扩展属性
 */
public record UserPrincipal(
	String userId,
	String zhName,
	Map<String, Object> attributes
) implements SecurityPrincipal {

	@Override
	public String getId() {
		return userId;
	}

	@Override
	public String getName() {
		return zhName;
	}
}
