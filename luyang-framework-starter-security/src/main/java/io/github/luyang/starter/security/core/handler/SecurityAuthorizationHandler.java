package io.github.luyang.starter.security.core.handler;

import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.constant.enums.error.SecurityError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 授权：没有权限访问时
 *
 * @author yang.lu
 */
public class SecurityAuthorizationHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request,
					   HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException {

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");

		try (PrintWriter writer = response.getWriter()) {
			writer.write(Result.failure(SecurityError.PERMISSION_DENIED).toString());
			writer.flush();
		}
	}
}
