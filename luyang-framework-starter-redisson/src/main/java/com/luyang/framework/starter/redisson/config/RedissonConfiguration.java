package com.luyang.framework.starter.redisson.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luyang.framework.starter.redisson.config.properties.RedissonProperties;
import com.luyang.framework.starter.redisson.helper.RedissonHelper;
import com.luyang.framework.starter.redisson.support.RedissonNameMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * Redisson 配置
 * 原版:{@link org.redisson.spring.starter.RedissonAutoConfiguration}
 *
 * @author yang.lu
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfiguration {

	private final ObjectMapper objectMapper;
	private final RedissonProperties redissonProperties;
	private final ApplicationContext applicationContext;

	@Bean
	public RedissonHelper redissonHelper(RedissonClient redissonClient) {
		return new RedissonHelper(redissonClient);
	}

	@Bean
	public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer() {
		return config -> {
			// 设置 JSON 编解码器
			config.setCodec(new JsonJacksonCodec(objectMapper));
			String keyPrefix = Optional.ofNullable(redissonProperties.getPrefix())
				.map(String::toUpperCase)
				.orElse(applicationContext.getEnvironment().getProperty("spring.application.name"));

			RedissonNameMapper redissonNameMapper = new RedissonNameMapper(keyPrefix);

			// 单机模式
			if (config.isSingleConfig()) {
				config.useSingleServer().setNameMapper(redissonNameMapper);
			}

			// 集群模式
			if (config.isClusterConfig()) {
				config.useClusterServers().setNameMapper(redissonNameMapper);
			}
		};
	}
}
