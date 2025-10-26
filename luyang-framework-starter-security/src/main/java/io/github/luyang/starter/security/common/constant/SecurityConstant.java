package io.github.luyang.starter.security.common.constant;

/**
 * Security 相关常量
 *
 * @author yang.lu
 */
public interface SecurityConstant {

    /** 认证 Token，用于验证用户身份的凭证 */
    String X_ACCESS_TOKEN = "X-Access-Token";

    /** 刷新 Token，用于获取新的认证 Token */
    String X_REFRESH_TOKEN = "X-Refresh-Token";
}
