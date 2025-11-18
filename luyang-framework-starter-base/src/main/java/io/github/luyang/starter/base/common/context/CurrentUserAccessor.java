package io.github.luyang.starter.base.common.context;

/**
 * 当前操作用户，共其他JAR方便获取当前操作用户信息
 *
 * @author yang.lu
 */
public interface CurrentUserAccessor {

	/**
	 * 获取当前操作人ID (User ID 或 Client ID)
	 *
	 * @author yang.lu
	 */
	String getOperatorId();

	/**
	 * 获取当前操作人名称
	 *
	 * @author yang.lu
	 */
	String getOperatorName();

	/**
	 * 获取当前操作人身份类型 ("USER" 或 "CLIENT")
	 * OperatorIdentityType
	 *
	 * @author yang.lu
	 */
	String getOit();
}

