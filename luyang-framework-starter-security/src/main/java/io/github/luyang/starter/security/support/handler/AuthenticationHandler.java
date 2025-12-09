package io.github.luyang.starter.security.support.handler;

import cn.hutool.extra.servlet.JakartaServletUtil;
import io.github.luyang.starter.base.model.Result;
import io.github.luyang.starter.security.common.enums.error.SecurityError;
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
public class AuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    @SuppressWarnings("deprecation")
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String content = Result.failure(SecurityError.MISSING_ACCESS_TOKEN).toString();
		JakartaServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}
