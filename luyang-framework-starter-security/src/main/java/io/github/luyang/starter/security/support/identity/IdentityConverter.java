package io.github.luyang.starter.security.support.identity;

import io.github.luyang.starter.security.remote.dto.TokenValidationResponse;
import io.github.luyang.starter.security.support.identity.principal.ClientPrincipal;
import io.github.luyang.starter.security.support.identity.principal.SecurityPrincipal;
import io.github.luyang.starter.security.support.identity.principal.UserPrincipal;

import java.util.Collections;
import java.util.Optional;

/**
 * 认证对象转换器
 *
 * @author yang.lu
 */
public final class IdentityConverter {

	private IdentityConverter() {
	}

	/**
	 * 将令牌内省结果转换为认证主体
	 *
	 * @author yang.lu
	 */
	public static AuthSubject convert(TokenValidationResponse response) {
		SecurityPrincipal principal = createPrincipal(response);

		return new AuthSubject(
			response.clientId(),
			principal,
			response.principalType(),
			Optional.ofNullable(response.scopes()).orElse(Collections.emptySet()),
			Optional.ofNullable(response.authorities()).orElse(Collections.emptySet()),
			Optional.ofNullable(response.attributes()).orElse(Collections.emptyMap())
		);
	}

	private static SecurityPrincipal createPrincipal(TokenValidationResponse response) {
		return switch (response.principalType()) {
			case USER -> new UserPrincipal(
				response.userId(),
				response.displayName(),
				response.attributes()
			);

			case CLIENT -> new ClientPrincipal(
				response.clientId(),
				response.displayName(),
				response.attributes()
			);
		};
	}
}
