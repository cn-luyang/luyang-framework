package io.github.luyang.starter.web.config;

import io.undertow.Undertow;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import static io.undertow.UndertowOptions.ENABLE_HTTP2;

/**
 * Undertow 配置类
 *
 * @author yang.lu
 */
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnClass(Undertow.class)
@EnableConfigurationProperties(ServerProperties.class)
@AutoConfigureBefore(ServletWebServerFactoryAutoConfiguration.class)
public class UndertowAutoConfig implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    private final ServerProperties serverProperties;

    /**
     * 自定义 Undertow 服务器配置
     *
     * @author yang.lu
     */
    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(isDirectBuffers(), getBufferSizeAsInt()));
            deploymentInfo.addServletContextAttribute(
                WebSocketDeploymentInfo.ATTRIBUTE_NAME,
                webSocketDeploymentInfo
            );
        });
    }

    /**
     * HTTP/2配置
     *
     * @author yang.lu
     */
    @Bean
    @ConditionalOnProperty(prefix = "server.http2", name = "enabled", havingValue = "true", matchIfMissing = true)
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> undertowHttp2Customizer() {
        return factory -> factory.addBuilderCustomizers(
            builder -> builder.setServerOption(ENABLE_HTTP2, true)
        );
    }

    private int getBufferSizeAsInt() {
        DataSize bufferSize = serverProperties.getUndertow().getBufferSize();
        if (null != bufferSize) {
            return (int) bufferSize.toBytes();
        }

        return 512;
    }

    private boolean isDirectBuffers() {
        Boolean directBuffers = serverProperties.getUndertow().getDirectBuffers();
        return null != directBuffers ? directBuffers : false;
    }
}
