package io.github.luyang.starter.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yang.lu
 */
@Getter
@Setter
@ConfigurationProperties(prefix = SecurityProperties.PREFIX)
public class SecurityProperties {

	public static final String PREFIX = "luyang.security";

	private Set<String> ignoreUrls = new HashSet<>();
}
