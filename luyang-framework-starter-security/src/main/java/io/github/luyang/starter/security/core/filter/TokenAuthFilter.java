package io.github.luyang.starter.security.core.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.UnifiedPrincipal;
import io.github.luyang.starter.security.constant.SecurityConstant;
import io.github.luyang.starter.security.constant.enums.error.SecurityError;
import io.github.luyang.starter.security.rpc.TokenValidationRpc;
import io.github.luyang.starter.security.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
public class TokenAuthFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(TokenAuthFilter.class);

	private final TokenValidationRpc tokenValidationRpc;

	public TokenAuthFilter(TokenValidationRpc tokenValidationRpc) {
		this.tokenValidationRpc = tokenValidationRpc;
	}

	/**
	 * 对每个请求进行拦截和处理
	 *
	 * @param request  HttpServletRequest 对象
	 * @param response HttpServletResponse 对象
	 * @param chain    FilterChain 对象，用于将请求传递给下一个过滤器
	 * @author yang.lu
	 */
	@Override
	@SuppressWarnings("NullableProblems")
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		// 尝试从请求中获取 Access_Token
		String accessToken = SecurityUtil.getAccessTokenValue(request);
		// 如果 Token 为空，则直接将请求传递给下一个过滤器，由后续的认证机制处理
		if (StrUtil.isBlank(accessToken)) {
			chain.doFilter(request, response);
			return;
		}

		UnifiedPrincipal unifiedPrincipal;

		try {
			Result<UnifiedPrincipal> unifiedPrincipalResult = tokenValidationRpc.validateToken(accessToken);
			if (!unifiedPrincipalResult.isSuccess() || BeanUtil.isEmpty(unifiedPrincipalResult.getData())) {
				writeUnauthorizedResponse(response, unifiedPrincipalResult.toString());
				return;
			}
			unifiedPrincipal = unifiedPrincipalResult.getData();
		} catch (Exception e) {
			logger.error("访问令牌认证异常", e);
			writeUnauthorizedResponse(response, Result.failure(SecurityError.ACCESS_TOKEN_VALIDATE_EXCEPTION).toString());
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			unifiedPrincipal,
			null,
			Collections.emptyList()
		);

		// 将 Web 请求的详细信息设置到 Authentication 对象中，例如 Session ID、远程地址等
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		// 将构建好的 Authentication 对象设置到 Spring Security 的安全上下文中，表示当前请求已经通过认证
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		// 填充认证信息
		fillAuthInfo(request, unifiedPrincipal);

		chain.doFilter(request, response);
	}

	/**
	 * 将认证用户信息填充到 HttpServletRequest 的属性中，供后续业务层读取使用。
	 *
	 * @param request          当前请求对象
	 * @param unifiedPrincipal 认证成功的统一用户信息
	 * @author yang.lu
	 */
	private void fillAuthInfo(HttpServletRequest request, UnifiedPrincipal unifiedPrincipal) {
		Optional.ofNullable(unifiedPrincipal)
			.ifPresent(principal -> {
				// 设置用户ID
				request.setAttribute(SecurityConstant.ATTR_USER_ID, principal.userId());
				// 设置客户端ID
				request.setAttribute(SecurityConstant.ATTR_CLIENT_ID, principal.clientId());
				// 设置主体类型（如 USER / CLIENT 等）
				request.setAttribute(SecurityConstant.ATTR_PRINCIPAL_TYPE, principal.principalType());
			});
	}

	@SuppressWarnings("deprecation")
	private void writeUnauthorizedResponse(HttpServletResponse response, String content) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		JakartaServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
	}
}
