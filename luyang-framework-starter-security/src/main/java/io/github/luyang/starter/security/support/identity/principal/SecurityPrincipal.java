package io.github.luyang.starter.security.support.identity.principal;

import java.io.Serializable;

/**
 * 身份信息接口
 *
 * @author yang.lu
 */
public sealed interface SecurityPrincipal extends Serializable permits UserPrincipal, ClientPrincipal {

	String getId();
	String getName();
}
