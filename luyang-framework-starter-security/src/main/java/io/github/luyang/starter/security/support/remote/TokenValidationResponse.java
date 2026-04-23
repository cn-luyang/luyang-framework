package io.github.luyang.starter.security.support.remote;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TokenValidationResponse implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;

	private boolean expired;
	private String userId;
	private String cnName;
	private LocalDateTime accessTokenExpiresTime;
}
