package com.luyang.framework.starter.security.config;

import com.luyang.framework.starter.security.filter.SecurityTokenAuthenticationFilter;
import com.luyang.framework.starter.security.handler.SecurityAuthenticationHandler;
import com.luyang.framework.starter.security.handler.SecurityAuthorizationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security资源服务器配置
 *
 * @author yang.lu
 */
@EnableWebSecurity
@EnableMethodSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityResourceConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain resourceSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
			.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.sessionManagement(AbstractHttpConfigurer::disable);

		httpSecurity
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint(new SecurityAuthenticationHandler())
				.accessDeniedHandler(new SecurityAuthorizationHandler())
			);

		httpSecurity
			.authorizeHttpRequests(request -> request
				// .requestMatchers(getAllAnonymousAccessMethods()).permitAll()
				.anyRequest()
				.authenticated()
			);

		// 自定义 Token Filter
		httpSecurity.addFilterBefore(new SecurityTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}
}
