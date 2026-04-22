package io.github.luyang.starter.security;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户身份信息
 *
 * @author yang.lu
 */
@Data
public final class UserIdentity {

	private String userId;
	private String cnName;
	private LocalDateTime accessTokenExpiresTime;
}
