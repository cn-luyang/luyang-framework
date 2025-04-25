package com.luyang.framework.starter.redisson.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luyang.framework.starter.base.util.SpringUtil;
import com.luyang.framework.starter.redisson.support.RedissonNameMapper;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置
 * 原版:{@link org.redisson.spring.starter.RedissonAutoConfiguration}
 *
 * @author yang.lu
 */
@AutoConfigureAfter(ObjectMapper.class)
@Configuration(proxyBeanMethods = false)
public class RedissonConfiguration {

	@Bean
	public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer() {
		return config -> {
			// 设置 JSON 编解码器
			config.setCodec(new JsonJacksonCodec(SpringUtil.getBean(ObjectMapper.class)));

			String keyPrefix = SpringUtil.getApplicationName().toUpperCase();
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
