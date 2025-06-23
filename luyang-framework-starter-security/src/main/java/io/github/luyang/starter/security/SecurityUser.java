package io.github.luyang.starter.security;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author yang.lu
 */
@Getter
@Setter
public class SecurityUser implements Serializable {

	@Serial
	private static final long serialVersionUID = 4758668578675934182L;

	private String clientId;
	private String userId;
	private String zhName;
	private String enName;
}
