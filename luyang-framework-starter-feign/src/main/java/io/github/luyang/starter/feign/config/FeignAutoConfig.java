package io.github.luyang.starter.feign.config;

import io.github.luyang.starter.feign.support.interceptor.FeignHeaderRelayInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author yang.lu
 */
@AutoConfiguration
public class FeignAutoConfig {

	@Bean
	public FeignHeaderRelayInterceptor feignHeaderRelayInterceptor() {
		return new FeignHeaderRelayInterceptor();
	}
}
