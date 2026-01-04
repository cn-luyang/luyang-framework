package io.github.luyang.starter.security.remote.dto;

import io.github.luyang.starter.security.common.enums.PrincipalType;
import io.github.luyang.starter.security.support.identity.Identity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
public class TokenValidationResponse implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;

	private boolean valid;
	private Long expiresAt;
	private PrincipalType principalType;
	private Set<String> scopes;
	private Set<String> authorities;
	private Identity identity;
}
