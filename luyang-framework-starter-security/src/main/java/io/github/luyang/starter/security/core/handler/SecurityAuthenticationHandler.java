package io.github.luyang.starter.security.core.handler;

import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.constant.enums.error.SecurityError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 身份验证：未登录
 *
 * @author yang.lu
 */
public class SecurityAuthenticationHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request,
						 HttpServletResponse response,
						 AuthenticationException authException) throws IOException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");

		try (var writer = response.getWriter()) {
			writer.write(Result.failure(SecurityError.MISSING_ACCESS_TOKEN).toString());
			writer.flush();
		}
	}
}
