package io.github.luyang.starter.security.support.identity.principal;

import java.util.Map;

/**
 * 客户端身份主体
 *
 * @param clientId   客户端ID
 * @param clientName 客户端名称
 * @param attributes 扩展属性
 */
public record ClientPrincipal(
	String clientId,
	String clientName,
	Map<String, Object> attributes
) implements SecurityPrincipal {

	@Override
	public String getId() {
		return clientId;
	}

	@Override
	public String getName() {
		return clientName;
	}
}
