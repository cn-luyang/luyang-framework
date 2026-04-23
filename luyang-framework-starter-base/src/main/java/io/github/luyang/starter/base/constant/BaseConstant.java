package io.github.luyang.starter.base.constant;

/**
 * @author yang.lu
 */
public interface BaseConstant {

	/**
	 * 用于在 HTTP 请求头（Header）中传递访问令牌的字段名
	 */
//	String AUTHORIZATION_HEADER = "Authorization";

	/**
	 * 用于在 URL 查询参数（Query Parameter）中传递刷新令牌的字段名
	 */
	String ACCESS_TOKEN_PARAM = "token";

	/**
	 * 用户ID的字段名
	 */
	String USER_ID = "userId";
}
