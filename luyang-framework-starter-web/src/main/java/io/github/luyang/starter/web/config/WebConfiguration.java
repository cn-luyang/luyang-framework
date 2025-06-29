package io.github.luyang.starter.web.config;

import io.github.luyang.starter.web.core.filter.TraceIdFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * Web 相关配置类
 *
 * @author yang.lu
 */
@AutoConfiguration
public class WebConfiguration {

	@Bean
	public FilterRegistrationBean<TraceIdFilter> traceIdFilter() {
		FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new TraceIdFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}
}
