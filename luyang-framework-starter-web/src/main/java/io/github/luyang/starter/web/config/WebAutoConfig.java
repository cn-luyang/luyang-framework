package io.github.luyang.starter.web.config;

import io.github.luyang.starter.web.core.advice.GlobalExceptionAdvice;
import io.github.luyang.starter.web.core.filter.TraceIdFilter;
import io.github.luyang.starter.web.core.initializer.BannerInitializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

/**
 * Web 相关配置类
 *
 * @author yang.lu
 */
@AutoConfiguration
public class WebAutoConfig {

	@Bean
	public FilterRegistrationBean<TraceIdFilter> traceIdFilter() {
		FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new TraceIdFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}

	@Bean
	public GlobalExceptionAdvice globalExceptionAdvice() {
		return new GlobalExceptionAdvice();
	}

	@Bean
	public BannerInitializer bannerInitializer(Environment environment, ApplicationContext context) {
		return new BannerInitializer(environment, context);
	}
}
