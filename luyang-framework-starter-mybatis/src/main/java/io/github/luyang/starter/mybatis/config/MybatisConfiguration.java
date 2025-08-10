package io.github.luyang.starter.mybatis.config;

import cn.hutool.core.net.NetUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.luyang.starter.mybatis.support.DefaultFieldHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Mybatis plus 配置
 *
 * @author yang.lu
 */
@Configuration(proxyBeanMethods = false)
@PropertySource(value = "classpath:mybatis-plus.properties", encoding = "UTF-8")
public class MybatisConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);

	/**
	 * 插件主体配置
	 *
	 * @return MybatisPlusInterceptor 插件主体
	 * @author yang.lu
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 分页插件
		interceptor.addInnerInterceptor(paginationInnerInterceptor());
		// 乐观锁插件
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		return interceptor;
	}

	/**
	 * 分页插件配置
	 *
	 * @return PaginationInnerInterceptor 分页插件
	 * @author yang.lu
	 */
	private PaginationInnerInterceptor paginationInnerInterceptor() {
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		paginationInnerInterceptor.setMaxLimit(-1L);
		// 分页合理化
		paginationInnerInterceptor.setOverflow(true);
		return paginationInnerInterceptor;
	}

	/**
	 * 配置自动填充处理器
	 *
	 * @return MetaObjectHandler 自动填充处理器
	 * @author yang.lu
	 */
	@Bean
	public MetaObjectHandler defaultFieldHandler() {
		return new DefaultFieldHandler();
	}

	/**
	 * 分布式ID生成器（基于主机地址）
	 *
	 * @return IdentifierGenerator ID生成器
	 * @author yang.lu
	 */
	@Bean
	public IdentifierGenerator identifierGenerator() {
		return new DefaultIdentifierGenerator(NetUtil.getLocalhost());
	}

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
