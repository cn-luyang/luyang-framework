package io.github.luyang.starter.security.constant;

/**
 * Security 相关常量
 *
 * @author yang.lu
 */
public interface SecurityConstant {

	/** 认证 Token，用于验证用户身份的凭证 */
	String LY_AUTH_TOKEN = "LY-Auth-Token";

	/** 刷新 Token，用于获取新的认证 Token */
	String LY_REFRESH_TOKEN = "LY-Refresh-Token";
}
