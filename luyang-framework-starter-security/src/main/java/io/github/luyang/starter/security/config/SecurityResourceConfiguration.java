package io.github.luyang.starter.security.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import io.github.luyang.starter.security.annotation.AnonymousAccess;
import io.github.luyang.starter.security.config.properties.SecurityProperties;
import io.github.luyang.starter.security.core.filter.SecurityTokenAuthenticationFilter;
import io.github.luyang.starter.security.core.handler.SecurityAuthenticationHandler;
import io.github.luyang.starter.security.core.handler.SecurityAuthorizationHandler;
import io.github.luyang.starter.security.rpc.TokenValidationRpc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * Security资源服务器配置
 *
 * @author yang.lu
 */
@EnableWebSecurity
@EnableMethodSecurity
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityResourceConfiguration {

	private final SecurityProperties securityProperties;
	private final RequestMappingHandlerMapping requestMappingHandlerMapping;

	@DubboReference(check = false, group = "platform-auth")
	private TokenValidationRpc tokenValidationRpc;

	public SecurityResourceConfiguration(SecurityProperties securityProperties, RequestMappingHandlerMapping requestMappingHandlerMapping) {
		this.securityProperties = securityProperties;
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;
	}

	/**
	 * 密码编码器
	 *
	 * @return 密码编码器
	 * @author yang.lu
	 */
	public @Bean PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 配置用户详情服务
	 * 阻止 Spring Security 尝试查找默认用户，从而避免项目启动时打印生成默认密码的警告
	 *
	 * @return 用户详情服务
	 * @author yang.lu
	 */
	public @Bean UserDetailsService userDetailsService() {
		return username -> {
			throw new UsernameNotFoundException("UserDetailsService not implemented");
		};
	}

	/**
	 * 配置资源服务器的安全过滤链
	 *
	 * @param httpSecurity 用于构建安全过滤链的 HttpSecurity 对象
	 * @return 构建好的 SecurityFilterChain 对象
	 * @author yang.lu
	 */
	public @Bean SecurityFilterChain resourceSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

		// 配置 HTTP 头部
		httpSecurity
			.headers(header ->
				// 禁用 X-Frame-Options 头，允许来自任何来源的 frame 嵌入
				header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			// 禁用默认的 Form 登录
			.formLogin(AbstractHttpConfigurer::disable)
			// 禁用默认的 Logout 登出
			.logout(AbstractHttpConfigurer::disable)
			// 禁用 HTTP Basic 认证
			.httpBasic(AbstractHttpConfigurer::disable)
			// 禁用 CSRF (跨站请求伪造)
			.csrf(AbstractHttpConfigurer::disable)
			// 配置跨域资源共享 (CORS) 允许所有来源、方法和头部
			.cors(Customizer.withDefaults())
			// 禁用 Session 管理 (使用 Token 进行认证，不需要 Session)
			.sessionManagement(AbstractHttpConfigurer::disable);

		// 配置异常处理
		httpSecurity
			.exceptionHandling(exceptionHandling ->
				exceptionHandling
					// 配置认证失败时的处理逻辑，使用自定义的 SecurityAuthenticationHandler
					.authenticationEntryPoint(new SecurityAuthenticationHandler())
					// 配置授权失败 (访问被拒绝) 时的处理逻辑，使用自定义的 SecurityAuthorizationHandler
					.accessDeniedHandler(new SecurityAuthorizationHandler())
			);

		// 配置请求授权规则
		httpSecurity
			.authorizeHttpRequests(request ->
				request
					// 允许对 getAnonymousUrls() 返回的 URL 列表进行匿名访问 (无需认证)
					.requestMatchers(getAnonymousUrls().toArray(new String[0])).permitAll()
					// 其他所有请求都需要进行认证
					.anyRequest()
					.authenticated()
			);

		// 在 UsernamePasswordAuthenticationFilter 前添加自定义的 SecurityTokenAuthenticationFilter
		httpSecurity.addFilterBefore(new SecurityTokenAuthenticationFilter(tokenValidationRpc), UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	/**
	 * 获取所有允许匿名访问的 Urls
	 *
	 * @return 匿名 Urls
	 * @author yang.lu
	 */
	private Set<String> getAnonymousUrls() {

		Set<String> anonymousUrls = new HashSet<>();

		// 添加配置中忽略的URL
		Set<String> ignoreUrls = securityProperties.getIgnoreUrls();
		if (CollUtil.isNotEmpty(ignoreUrls)) {
			anonymousUrls.addAll(ignoreUrls);
		}

		// 获取所有请求映射信息和对应的处理方法
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		if (MapUtil.isEmpty(handlerMethods)) {
			return anonymousUrls;
		}

		// 遍历所有请求映射，查找带有 @AnonymousAccess 注解的方法或类
		handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
			// 检查方法或类上是否存在 @AnonymousAccess 注解
			boolean isMethodAnonymous = AnnotationUtils
				.findAnnotation(handlerMethod.getMethod(), AnonymousAccess.class) != null;
			boolean isClassAnonymous = AnnotationUtils
				.findAnnotation(handlerMethod.getBeanType(), AnonymousAccess.class) != null;

			// 如果方法或类标记为允许匿名访问，则提取其URL路径并添加到集合中
			if (isMethodAnonymous || isClassAnonymous) {
				Optional.ofNullable(requestMappingInfo.getPathPatternsCondition())
					.map(PathPatternsRequestCondition::getPatternValues)
					.ifPresent(anonymousUrls::addAll);
			}
		});

		return anonymousUrls;
	}
}
