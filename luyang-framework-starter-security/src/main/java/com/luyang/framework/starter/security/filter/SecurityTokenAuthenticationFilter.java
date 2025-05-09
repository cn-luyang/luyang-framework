package com.luyang.framework.starter.security.filter;

import cn.hutool.core.util.StrUtil;
import com.luyang.framework.starter.security.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Token 认证过滤器
 *
 * @author yang.lu
 */
public class SecurityTokenAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(SecurityTokenAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		String authToken = SecurityUtil.getTokenValue(request);
		if (StrUtil.isBlank(authToken)) {
			chain.doFilter(request, response);
			return;
		}

		// TODO: 远程调用认证服务、验证token是否有效返回用户信息

		chain.doFilter(request, response);
	}
}
