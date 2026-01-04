package io.github.luyang.starter.security.support.identity;

import io.github.luyang.starter.security.common.enums.PrincipalType;
import lombok.Data;

/**
 * 用户身份信息
 *
 * @author yang.lu
 */
@Data
public final class UserIdentity implements Identity {

	private String type = PrincipalType.USER.name();

	private String clientId;
	private String clientName;
	private String userId;
	private String zhName;
}
