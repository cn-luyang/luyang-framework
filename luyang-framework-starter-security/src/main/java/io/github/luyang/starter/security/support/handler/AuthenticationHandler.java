package io.github.luyang.starter.security.support.handler;

import cn.hutool.extra.servlet.JakartaServletUtil;
import io.github.luyang.starter.base.model.Result;
import io.github.luyang.starter.security.common.enums.SecurityErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
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

		String content = Result.failure(matchError(authException)).toString();
		JakartaServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

	private SecurityErrorCode matchError(AuthenticationException e) {
		// Token 缺失
		if (e instanceof InsufficientAuthenticationException) {
			return SecurityErrorCode.TOKEN_MISSING;
		}
		// Token 过期
		if (e instanceof CredentialsExpiredException) {
			return SecurityErrorCode.TOKEN_EXPIRED;
		}
		// Token 无效
		if (e instanceof BadCredentialsException) {
			return SecurityErrorCode.TOKEN_INVALID;
		}

		// 认证失败
		return SecurityErrorCode.TOKEN_AUTHENTICATION_FAILED;
	}
}
