package com.luyang.framework.starter.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author yang.lu
 */
public class SecurityUser {

	private String appId;
	private String appName;
	private String userId;
	private String name;
}
