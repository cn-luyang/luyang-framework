package io.github.luyang.starter.security.remote.dto;

/**
 * @author yang.lu
 */
public record AccessTokenResp(
    String accessToken,
    String tokenType,
    Long expiresIn
) {
}
