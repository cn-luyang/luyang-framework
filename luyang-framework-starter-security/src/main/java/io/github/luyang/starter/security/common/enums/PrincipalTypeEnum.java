package io.github.luyang.starter.security.common.enums;

/**
 * 认证主体的类型
 *
 * @author yang.lu
 */

public enum PrincipalTypeEnum {

    /**
     * 系统用户
     */
    USER,

    /**
     * 客户端（如后端服务、第三方应用）
     */
    CLIENT;
}
