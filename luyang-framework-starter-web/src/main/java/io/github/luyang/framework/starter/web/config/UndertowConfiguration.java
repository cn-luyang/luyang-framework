package io.github.luyang.framework.starter.web.config;

import io.undertow.Undertow;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

import static io.undertow.UndertowOptions.ENABLE_HTTP2;

/**
 * Undertow 配置类
 *
 * @author yang.lu
 */
@AutoConfiguration
@ConditionalOnClass(Undertow.class)
@AutoConfigureBefore(ServletWebServerFactoryAutoConfiguration.class)
public class UndertowConfiguration implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

	/*
		@AutoConfiguration 自动配置类，Spring Boot 启动时会自动加载
		@ConditionalOnClass(Undertow.class) classpath 中存在 Undertow 时才生效
		@AutoConfigureBefore(ServletWebServerFactoryAutoConfiguration.class) 在默认 Web 服务器配置之前生效
	 */

	/**
	 * 添加自定义部署的Web应用程序的配置信息
	 *
	 * @author yang.lu
	 */
	@Override
	public void customize(UndertowServletWebServerFactory factory) {
		factory.addDeploymentInfoCustomizers(deploymentInfo -> {
			WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
			// 设置 WebSocket 的缓冲池，512 字节非直接内存缓冲池
			webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 512));
			// 将 WebSocket 部署信息绑定到 servlet 上下文属性中，供 Undertow 使用
			deploymentInfo.addServletContextAttribute(
				"io.undertow.websockets.jsr.WebSocketDeploymentInfo",
				webSocketDeploymentInfo
			);
		});
	}

	/**
	 * 添加自定义的构建器定制器，启用对HTTP/2的支持
	 *
	 * @author yang.lu
	 */
	@Bean
	public WebServerFactoryCustomizer<UndertowServletWebServerFactory> undertowHttp2WebServerFactoryCustomizer() {
		return factory -> factory.addBuilderCustomizers(
			builder -> builder.setServerOption(ENABLE_HTTP2, true)
		);
	}
}
