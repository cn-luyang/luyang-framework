package io.github.luyang.starter.base.constant;

/**
 * @author yang.lu
 */
public interface BaseConstant {

	/** HttpServletRequest 中存储用户ID的属性名 */
	String ATTR_USER_ID = "auth.user-id";

	/** HttpServletRequest 中存储客户端ID的属性名 */
	String ATTR_CLIENT_ID = "auth.client-id";

	/** HttpServletRequest 中存储当前登录主体类型的属性名 */
	String ATTR_PRINCIPAL_TYPE = "auth.principal-type";
}
