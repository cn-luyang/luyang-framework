package io.github.luyang.framework.starter.security.handler;

import io.github.luyang.framework.starter.base.api.Result;
import io.github.luyang.framework.starter.security.constant.SecurityErrorEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 身份验证：未登录或登录过期
 *
 * @author yang.lu
 */
public class SecurityAuthenticationHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
//		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		Result<Void> result = Result.failure(SecurityErrorEnum.UNAUTHORIZED);
		try (PrintWriter writer = response.getWriter()) {
			writer.write(result.toString());
			writer.flush();
		}
	}
}
