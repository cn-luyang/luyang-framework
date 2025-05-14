package io.github.luyang.starter.security.filter;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.github.luyang.starter.security.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Token 认证过滤器
 *
 * @author yang.lu
 */
public class SecurityTokenAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(SecurityTokenAuthenticationFilter.class);

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

		// 尝试从请求中获取 Token
		String authToken = StrUtil.blankToDefault(SecurityUtil.getTokenValue(request), "123");
		// 如果 Token 为空，则直接将请求传递给下一个过滤器，由后续的认证机制处理
		if (StrUtil.isBlank(authToken)) {
			chain.doFilter(request, response);
			return;
		}

		// TODO: 远程调用认证服务、验证token是否有效返回用户信息
		Map<String, String> map = MapUtil.newHashMap();
		map.put("appId", "ee647812e5404687abe21cdc55cbe8e5");
		map.put("appName", "Test");
		map.put("userId", "472dba35eefa42b7bfd734a4f6623142");
		map.put("name", "luyang");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			map,
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
