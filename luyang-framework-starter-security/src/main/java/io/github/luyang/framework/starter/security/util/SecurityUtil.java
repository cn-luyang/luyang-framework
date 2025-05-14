package io.github.luyang.framework.starter.security.util;

import cn.hutool.core.util.StrUtil;
import io.github.luyang.framework.starter.security.constant.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Security 相关工具类
 *
 * @author yang.lu
 */
public class SecurityUtil {

	/**
	 * 从当前请求中获取认证令牌
	 *
	 * @param request 当前请求
	 * @return 令牌
	 * @author yang.lu
	 */
	public static String getTokenValue(HttpServletRequest request) {

		String token = StrUtil.blankToDefault(
			request.getHeader(SecurityConstant.LY_AUTH_TOKEN),
			request.getParameter(SecurityConstant.LY_AUTH_TOKEN)
		);

		if (StrUtil.isBlank(token)) {
			return null;
		}

		return StrUtil.removePrefix(token, "Bearer ");
	}
}
