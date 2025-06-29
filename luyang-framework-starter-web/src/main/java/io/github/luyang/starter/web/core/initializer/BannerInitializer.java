package io.github.luyang.starter.web.core.initializer;

import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

/**
 * 自定义 Banner 初始化器
 *
 * @author yang.lu
 */
public record BannerInitializer(Environment environment) implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(BannerInitializer.class);

	@Override
	public void run(ApplicationArguments args) {
		ThreadUtil.execute(() -> {
			// 延迟确保日志系统初始化完成
			ThreadUtil.sleep(800, TimeUnit.MILLISECONDS);
			logger.info("Application started successfully!");
		});
	}
}
