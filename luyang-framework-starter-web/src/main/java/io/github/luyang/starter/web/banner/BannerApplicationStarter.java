package io.github.luyang.starter.web.banner;

import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author yang.lu
 */
public record BannerApplicationStarter(Environment environment) implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(BannerApplicationStarter.class);

	@Override
	public void run(ApplicationArguments args) {
		ThreadUtil.execute(() -> {
			ThreadUtil.sleep(1, TimeUnit.SECONDS);
			logger.info("Application started successfully!");
		});
	}
}
