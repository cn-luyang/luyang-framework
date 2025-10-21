package io.github.luyang.starter.security.remote.dto;

/**
 * @author yang.lu
 */
public record CheckTokenResp(
	Boolean active,
	String grantType,
	Long expiresIn,
	String clientId
) {
}
