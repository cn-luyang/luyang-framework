package io.github.luyang.starter.security.core.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.UnifiedPrincipal;
import io.github.luyang.starter.security.rpc.TokenValidationRpc;
import io.github.luyang.starter.security.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Token 认证过滤器
 *
 * @author yang.lu
 */
public class SecurityTokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenValidationRpc tokenValidationRpc;

	public SecurityTokenAuthenticationFilter(TokenValidationRpc tokenValidationRpc) {
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
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		// 尝试从请求中获取 Access_Token
		String accessToken = SecurityUtil.getAccessTokenValue(request);
		// 如果 Token 为空，则直接将请求传递给下一个过滤器，由后续的认证机制处理
		if (StrUtil.isBlank(accessToken)) {
			chain.doFilter(request, response);
			return;
		}

		Result<UnifiedPrincipal> unifiedPrincipalResult = tokenValidationRpc.validateToken(accessToken);
		if (!unifiedPrincipalResult.isSuccess() || BeanUtil.isEmpty(unifiedPrincipalResult.getData())) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");

			try (var writer = response.getWriter()) {
				writer.write(unifiedPrincipalResult.toString());
				writer.flush();
			}

			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			unifiedPrincipalResult.getData(),
			null,
			Collections.emptyList()
		);

		// 将 Web 请求的详细信息设置到 Authentication 对象中，例如 Session ID、远程地址等
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		// 将构建好的 Authentication 对象设置到 Spring Security 的安全上下文中，表示当前请求已经通过认证
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		chain.doFilter(request, response);
	}
}
