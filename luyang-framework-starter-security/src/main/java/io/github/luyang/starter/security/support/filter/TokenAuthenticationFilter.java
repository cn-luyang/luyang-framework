package io.github.luyang.starter.security.support.filter;

import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.base.model.Result;
import io.github.luyang.starter.security.UserIdentity;
import io.github.luyang.starter.security.support.remote.RemoteAuthClient;
import io.github.luyang.starter.security.support.remote.TokenValidationResponse;
import io.github.luyang.starter.security.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * Token 认证过滤器
 *
 * @author yang.lu
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	/**
	 * 远程 Token 验证服务
	 */
	private final RemoteAuthClient remoteAuthClient;

	/**
	 * 对每个请求进行Token验证和认证处理
	 *
	 * @param request  HTTP请求对象
	 * @param response HTTP响应对象
	 * @param chain    过滤器链
	 * @throws ServletException Servlet异常
	 * @throws IOException      IO异常
	 * @author yang.lu
	 */
	@Override
	@SuppressWarnings("NullableProblems")
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		// 从请求中提取 Token
		String token = SecurityUtil.extractToken(request);

		// 如果Token为空，直接放行（由后续认证机制处理）
		if (StrUtil.isBlank(token)) {
			chain.doFilter(request, response);
			return;
		}

		// 验证 Token 有效性
		TokenValidationResponse tokenValidationResponse = validateToken(token);
		// 设置认证信息到 Security 上下文
		setupSecurityContext(request, tokenValidationResponse);
		// 继续过滤器链
		chain.doFilter(request, response);
	}

	/**
	 * 验证Token有效性
	 * 调用远程服务验证Token并返回用户主体信息
	 *
	 * @param token 访问令牌
	 * @return 统一用户主体信息
	 * @author yang.lu
	 */
	private TokenValidationResponse validateToken(String token) {
		// 调用远程Token验证服务
		Result<TokenValidationResponse> authUserResult = remoteAuthClient.validateToken(token);
		TokenValidationResponse resp = Optional.ofNullable(authUserResult)
			.filter(Result::isSuccess)
			.map(Result::getData)
			.orElseThrow(() -> new BadCredentialsException(null));

		if (!resp.isExpired()) {
			throw new CredentialsExpiredException(null);
		}

		return resp;
	}

	/**
	 * 设置安全上下文
	 * 创建 Authentication 对象并设置到 SecurityContextHolder
	 *
	 * @param request     HTTP请求
	 * @author yang.lu
	 */
	private void setupSecurityContext(HttpServletRequest request, TokenValidationResponse resp) {

		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUserId(resp.getUserId());
		userIdentity.setCnName(resp.getCnName());
		userIdentity.setAccessTokenExpiresTime(resp.getAccessTokenExpiresTime());

		var authenticationToken = new UsernamePasswordAuthenticationToken(userIdentity, null, Collections.emptyList());
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
}
