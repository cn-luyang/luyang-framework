package io.github.luyang.starter.mybatis.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author yang.lu
 */
@AutoConfiguration
public class DataSourceAutoConfig {

	private static final Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);

	/**
	 * 更早初始化连接池，避免首次请求延迟
	 *
	 * @param dataSource 数据源
	 * @return org.springframework.boot.ApplicationRunner
	 * @author yang.lu
	 */
	@Bean
	public ApplicationRunner dataSourceInitializer(DataSource dataSource) {
		return args -> {
			logger.info("Initializing DataSource connection...");
			try (Connection connection = dataSource.getConnection();
				 Statement statement = connection.createStatement()) {
				// 测试连接
				statement.execute("SELECT 1");
				logger.info("DataSource initialized successfully: {}", dataSource);
			} catch (Exception e) {
				logger.error("Failed to initialize DataSource!", e);
				throw new IllegalStateException("DataSource initialization failed", e);
			}
		};
	}
}
