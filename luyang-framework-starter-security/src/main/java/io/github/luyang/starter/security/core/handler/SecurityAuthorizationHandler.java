package io.github.luyang.starter.security.core.handler;

import cn.hutool.extra.servlet.JakartaServletUtil;
import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.constant.enums.error.SecurityError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 授权：没有权限访问时
 *
 * @author yang.lu
 */
public class SecurityAuthorizationHandler implements AccessDeniedHandler {

	@Override
	@SuppressWarnings("deprecation")
	public void handle(HttpServletRequest request,
					   HttpServletResponse response,
					   AccessDeniedException accessDeniedException) {

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		String content = Result.failure(SecurityError.PERMISSION_DENIED).toString();
		JakartaServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
	}
}
