package io.github.luyang.starter.security.config;

import io.github.luyang.starter.security.remote.AuthTokenRemoteService;
import io.github.luyang.starter.security.support.context.SecurityCurrentUserAccessor;
import io.github.luyang.starter.security.support.filter.TokenAuthenticationFilter;
import io.github.luyang.starter.security.support.handler.AuthenticationHandler;
import io.github.luyang.starter.security.support.handler.AuthorizationHandler;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security Bean 配置类
 *
 * @author yang.lu
 */
@Configuration
public class SecurityBeanConfig {

	@DubboReference(providedBy = "platform-uaa")
	private AuthTokenRemoteService authTokenRemoteService;

    /**
     * 配置密码编码器
     * 使用BCrypt强哈希算法进行密码加密和验证
     *
     * @return BCryptPasswordEncoder 实例
     * @author yang.lu
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置用户详情服务
     * 阻止Spring Security尝试查找默认用户，避免项目启动时打印生成默认密码的警告
     *
     * @return UserDetailsService 实例
     * @author yang.lu
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UsernameNotFoundException("UserDetailsService not implemented");
        };
    }

    /**
     * 配置认证异常处理器
     * 处理认证失败时的逻辑（如未登录访问受保护资源）
     *
     * @return AuthenticationHandler 实例
     * @author yang.lu
     */
    @Bean
    public AuthenticationHandler authenticationHandler() {
        return new AuthenticationHandler();
    }

    /**
     * 配置授权异常处理器
     * 处理授权失败时的逻辑（如权限不足）
     *
     * @return AuthorizationHandler 实例
     * @author yang.lu
     */
    @Bean
    public AuthorizationHandler authorizationHandler() {
        return new AuthorizationHandler();
    }

    /**
     * 配置Token认证过滤器
     *
     * @return TokenAuthenticationFilter 实例
     * @author yang.lu
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(authTokenRemoteService);
    }

	/**
	 * 配置Token认证过滤器
	 *
	 * @return TokenAuthenticationFilter 实例
	 * @author yang.lu
	 */
	@Bean
	public SecurityCurrentUserAccessor securityCurrentUserAccessor() {
		return new SecurityCurrentUserAccessor();
	}
}
