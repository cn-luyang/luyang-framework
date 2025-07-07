package io.github.luyang.starter.security.core.handler;

import cn.hutool.extra.servlet.JakartaServletUtil;
import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.constant.enums.error.SecurityError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 身份验证：未登录
 *
 * @author yang.lu
 */
public class SecurityAuthenticationHandler implements AuthenticationEntryPoint {

	@Override
	@SuppressWarnings("deprecation")
	public void commence(HttpServletRequest request,
						 HttpServletResponse response,
						 AuthenticationException authException) {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		String content = Result.failure(SecurityError.ACCESS_TOKEN_MISSING).toString();
		JakartaServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
	}
}
