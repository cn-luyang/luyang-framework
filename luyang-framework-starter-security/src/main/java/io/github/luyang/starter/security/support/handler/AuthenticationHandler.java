package io.github.luyang.starter.security.support.handler;

import cn.hutool.extra.servlet.JakartaServletUtil;
import io.github.luyang.starter.base.model.Result;
import io.github.luyang.starter.security.common.enums.error.SecurityError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 身份验证：未登录
 *
 * @author yang.lu
 */
public class AuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    @SuppressWarnings("deprecation")
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		String content = Result.failure(matchError(authException)).toString();
		JakartaServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

	private SecurityError matchError(AuthenticationException e) {
		if (e instanceof InternalAuthenticationServiceException) {
			return SecurityError.AUTH_SERVICE_UNAVAILABLE;
		}
		// Token 缺失
		if (e instanceof InsufficientAuthenticationException) {
			return SecurityError.TOKEN_MISSING;
		}
		// Token 过期
		if (e instanceof CredentialsExpiredException) {
			return SecurityError.TOKEN_EXPIRED;
		}
		// Token 无效
		if (e instanceof BadCredentialsException) {
			return SecurityError.TOKEN_INVALID;
		}

		// 认证失败
		return SecurityError.TOKEN_AUTHENTICATION_FAILED;
	}
}
