package io.github.luyang.starter.security.support.identity;

import io.github.luyang.starter.security.common.enums.PrincipalType;
import lombok.Data;

/**
 * 客户端身份信息
 *
 * @author yang.lu
 */
@Data
public final class ClientIdentity implements Identity {

	private String type = PrincipalType.CLIENT.name();

	private String clientId;
	private String clientName;
}
