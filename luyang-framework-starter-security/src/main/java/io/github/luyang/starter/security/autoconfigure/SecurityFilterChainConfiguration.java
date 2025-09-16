package io.github.luyang.starter.security.autoconfigure;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import io.github.luyang.starter.security.annotation.Anonymous;
import io.github.luyang.starter.security.properties.SecurityProperties;
import io.github.luyang.starter.security.web.filter.TokenAuthenticationFilter;
import io.github.luyang.starter.security.web.handler.AuthenticationHandler;
import io.github.luyang.starter.security.web.handler.AuthorizationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 安全过滤链配置类
 * 负责配置HTTP安全规则和请求过滤链
 *
 * @author yang.lu
 */
@Configuration
@RequiredArgsConstructor
public class SecurityFilterChainConfiguration {

	// 安全配置属性
	private final SecurityProperties securityProperties;
	// 请求映射处理器，用于获取所有Controller方法的映射信息
	private final RequestMappingHandlerMapping requestMappingHandlerMapping;
	// 认证异常处理器
	private final AuthenticationHandler authenticationHandler;
	// 授权异常处理器
	private final AuthorizationHandler authorizationHandler;
	// Token认证过滤器
	private final TokenAuthenticationFilter tokenAuthenticationFilter;


	/**
	 * 配置资源服务器的安全过滤链
	 * 定义整个应用的HTTP安全规则
	 *
	 * @param httpSecurity HttpSecurity配置对象
	 * @return 配置完成的安全过滤链
	 * @throws Exception 配置过程中可能出现的异常
	 * @author yang.lu
	 */
	@Bean
	public SecurityFilterChain resourceSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// 配置 HTTP 头部
		httpSecurity
			.headers(header ->
				// 禁用 X-Frame-Options 头，允许来自任何来源的 frame 嵌入
				header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			// 禁用默认的表单登录
			.formLogin(AbstractHttpConfigurer::disable)
			// 禁用默认的登出功能
			.logout(AbstractHttpConfigurer::disable)
			// 禁用 HTTP Basic 认证
			.httpBasic(AbstractHttpConfigurer::disable)
			// 禁用CSRF(跨站请求伪造)保护,因为使用Token认证
			.csrf(AbstractHttpConfigurer::disable)
			// 配置跨域资源共享 (CORS) 允许所有来源、方法和头部
			.cors(Customizer.withDefaults())
			// 禁用 Session 管理 (使用 Token 进行认证，不需要 Session)
			.sessionManagement(AbstractHttpConfigurer::disable);

		// 配置异常处理机制
		httpSecurity
			.exceptionHandling(exceptionHandling ->
				exceptionHandling
					// 设置认证失败处理器（未登录时）
					.authenticationEntryPoint(authenticationHandler)
					// 设置授权失败处理器（权限不足时）
					.accessDeniedHandler(authorizationHandler)
			);

		// 配置请求授权规则
		httpSecurity
			.authorizeHttpRequests(request ->
				request
					// 允许匿名访问的URL（配置文件和注解标记的）
					.requestMatchers(getAnonymousUrls().toArray(new String[0])).permitAll()
					// 其他所有请求都需要认证
					.anyRequest()
					.authenticated()
			);

		// 配置自定义过滤器，在 UsernamePasswordAuthenticationFilter 前添加自定义的 SecurityTokenAuthenticationFilter
		httpSecurity.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	/**
	 * 获取所有允许匿名访问的URL集合
	 * 包括配置文件中配置的和通过注解标记的URL
	 *
	 * @return 匿名URL集合
	 * @author yang.lu
	 */
	private Set<String> getAnonymousUrls() {
		Set<String> anonymousUrls = new HashSet<>();

		// 添加配置文件中配置的忽略URL
		Set<String> ignoreUrls = securityProperties.getIgnoreUrls();
		if (CollUtil.isNotEmpty(ignoreUrls)) {
			anonymousUrls.addAll(ignoreUrls);
		}

		// 添加通过@Anonymous注解标记的URL
		anonymousUrls.addAll(getAnonymousUrlsFromAnnotations());

		return anonymousUrls;
	}

	/**
	 * 从Controller方法注解中获取匿名访问URL
	 * 扫描所有带有@Anonymous注解的方法和类
	 *
	 * @return 注解标记的匿名URL集合
	 * @author yang.lu
	 */
	private Set<String> getAnonymousUrlsFromAnnotations() {
		Set<String> anonymousUrls = new HashSet<>();
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

		// 如果没有处理器方法，直接返回空集合
		if (MapUtil.isEmpty(handlerMethods)) {
			return anonymousUrls;
		}

		// 遍历所有处理器方法，检查@Anonymous注解
		handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
			// 检查方法级别注解
			boolean isMethodAnonymous = AnnotationUtils
				.findAnnotation(handlerMethod.getMethod(), Anonymous.class) != null;
			// 检查类级别注解
			boolean isClassAnonymous = AnnotationUtils
				.findAnnotation(handlerMethod.getBeanType(), Anonymous.class) != null;

			// 如果方法或类标记为匿名访问，提取URL模式
			if (isMethodAnonymous || isClassAnonymous) {
				Optional.ofNullable(requestMappingInfo.getPathPatternsCondition())
					.map(PathPatternsRequestCondition::getPatternValues)
					.ifPresent(anonymousUrls::addAll);
			}
		});

		return anonymousUrls;
	}
}
