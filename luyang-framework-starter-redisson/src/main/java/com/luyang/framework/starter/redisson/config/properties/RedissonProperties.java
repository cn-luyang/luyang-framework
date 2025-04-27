package com.luyang.framework.starter.redisson.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redisson 配置属性
 *
 * @author yang.lu
 */
@Getter
@Setter
@ConfigurationProperties(prefix = RedissonProperties.PREFIX)
public class RedissonProperties {

	public static final String PREFIX = "spring.data.redis";

	private String prefix;
}
