package io.github.luyang.framework.starter.security.handler;

import io.github.luyang.framework.starter.base.api.Result;
import io.github.luyang.framework.starter.security.constant.SecurityErrorEnum;
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
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
//		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setStatus(HttpServletResponse.SC_OK);
		Result<Void> result = Result.failure(SecurityErrorEnum.FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		try (PrintWriter writer = response.getWriter()) {
			writer.write(result.toString());
			writer.flush();
		}
	}
}
