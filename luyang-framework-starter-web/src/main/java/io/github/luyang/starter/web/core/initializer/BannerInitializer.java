package io.github.luyang.starter.web.core.initializer;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

/**
 * 自定义 Banner 初始化器
 *
 * @author yang.lu
 */
public record BannerInitializer(Environment environment, ApplicationContext context) implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(BannerInitializer.class);
	private static final String BLUE = "\u001B[34m";
	private static final String RESET = "\u001B[0m";

	@Override
	public void run(ApplicationArguments args) {
		ThreadUtil.execute(() -> {

			// 延迟确保日志系统初始化完成
			ThreadUtil.sleep(800, TimeUnit.MILLISECONDS);

			String bootVersion = SpringBootVersion.getVersion();
			String serverName = environment.getProperty("spring.application.name");
			String ip = NetUtil.getLocalhostStr();
			String port = resolvePort();

			logger.info(BLUE + "Application started successfully!" + RESET);

			if (StrUtil.isNotBlank(bootVersion)) {
				logger.info(BLUE + "Spring Boot :: {}" + RESET, bootVersion);
			}

			if (StrUtil.isNotBlank(serverName)) {
				logger.info(BLUE + "Application :: {}" + RESET, serverName);
			}

			if (StrUtil.isNotBlank(port)) {
				logger.info(BLUE + "Local       :: http://localhost:{}/" + RESET, port);
				if (StrUtil.isNotBlank(ip)) {
					logger.info(BLUE + "External    :: http://{}:{}/" + RESET, ip, port);
				}
			}
		});
	}

	private String resolvePort() {
		if ((context instanceof WebServerApplicationContext webCtx)) {
			WebServer webServer = webCtx.getWebServer();
			if (null != webServer) {
				return StrUtil.toStringOrEmpty(webServer.getPort());
			}
		}

		return null;
	}
}
