package io.github.luyang.starter.security.support.filter;

import io.github.luyang.base.util.ServletUtil;
import io.github.luyang.base.util.StrUtil;
import io.github.luyang.starter.base.common.model.Result;
import io.github.luyang.starter.base.common.model.ResultOps;
import io.github.luyang.starter.security.common.enums.error.SecurityError;
import io.github.luyang.starter.security.remote.AuthTokenRemoteService;
import io.github.luyang.starter.security.remote.dto.TokenValidationResponse;
import io.github.luyang.starter.security.support.identity.AuthSubject;
import io.github.luyang.starter.security.support.identity.IdentityConverter;
import io.github.luyang.starter.security.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Token 认证过滤器
 *
 * @author yang.lu
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	/**
	 * 远程Token验证服务
	 */
	private final AuthTokenRemoteService authTokenRemoteService;

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

		try {
			// 验证Token有效性
			TokenValidationResponse tokenValidationResponse = validateToken(token);
			// 转换身份主体
			AuthSubject authSubject = IdentityConverter.convert(tokenValidationResponse);
			// 设置认证信息到Security上下文
			setupSecurityContext(request, authSubject);
			// 填充认证信息到请求属性
			populateRequestAttributes(request, authSubject);
			// 继续过滤器链
			chain.doFilter(request, response);
		} catch (AuthenticationException e) {
			logger.error("访问令牌认证异常", e);
			// 处理认证异常
			handleAuthenticationException(response);
		}
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
		Result<TokenValidationResponse> authUserResult = authTokenRemoteService.validateToken(token);

		TokenValidationResponse tokenValidationResponse = ResultOps.of(authUserResult)
			.ifFailure(r -> new AuthenticationServiceException("令牌验证失败: " + r.getMessage()))
			.getOrThrow(() -> new UsernameNotFoundException("未获取到Token验证信息"));

		if (!tokenValidationResponse.valid()) {
			throw new BadCredentialsException("令牌无效或已过期");
		}

		return tokenValidationResponse;
	}

	/**
	 * 设置安全上下文
	 * 创建 Authentication 对象并设置到 SecurityContextHolder
	 *
	 * @param request     HTTP请求
	 * @param authSubject 认证主体
	 * @author yang.lu
	 */
	private void setupSecurityContext(HttpServletRequest request, AuthSubject authSubject) {
		// 将 scopes 转换为 GrantedAuthority
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		// 添加 Scope 权限
//		if (authSubject.scopes() != null) {
//			authSubject.scopes().forEach(scope ->
//				authorities.add(new SimpleGrantedAuthority("SCOPE_" + scope))
//			);
//		}

		// 添加功能权限
//		if (authSubject.authorities() != null) {
//			authSubject.authorities().forEach(role ->
//				authorities.add(new SimpleGrantedAuthority("ROLE_" + role))
//			);
//		}

		// 创建认证令牌（无凭证，无权限）
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			authSubject, null, authorities
		);
		// 设置Web认证详情（IP地址、Session ID等）
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		// 设置认证信息到安全上下文
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	/**
	 * 填充认证信息到请求属性
	 * 将用户信息设置到请求属性中供业务层使用
	 *
	 * @param request         HTTP请求
	 * @param authSubject 认证主体
	 * @author yang.lu
	 */
	private void populateRequestAttributes(HttpServletRequest request, AuthSubject authSubject) {
		// 根据主体类型设置特定属性
//		if (authSubject.isUser()) {
//			request.setAttribute(BaseConstant.ATTR_USER_ID, authSubject.getId());
//		} else if (authSubject.isClient()) {
//			request.setAttribute(BaseConstant.ATTR_CLIENT_ID, authSubject.getId());
//		}
	}

	/**
	 * 处理认证异常
	 * 返回统一的错误响应格式
	 *
	 * @param response HTTP响应
	 * @author yang.lu
	 */
	private void handleAuthenticationException(HttpServletResponse response) {
		// 设置HTTP状态码为401未授权
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		// 设置响应内容类型为JSON
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		// 设置字符编码
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		// 创建错误响应结果
		Result<?> errorResult = Result.failure(SecurityError.VALIDATE_EXCEPTION_ACCESS_TOKEN);
		// 将错误结果序列化为JSON并写入响应
		ServletUtil.write(response, errorResult.toString(), MediaType.APPLICATION_JSON_UTF8_VALUE);
	}
}
