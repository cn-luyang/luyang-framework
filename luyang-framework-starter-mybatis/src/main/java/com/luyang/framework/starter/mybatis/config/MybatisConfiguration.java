package com.luyang.framework.starter.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis plus 配置
 *
 * @author yang.lu
 */
@Configuration(proxyBeanMethods = false)
public class MybatisConfiguration {


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
		interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
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
	 * 乐观锁插件配置
	 *
	 * @return OptimisticLockerInnerInterceptor 乐观锁插件
	 * @author yang.lu
	 */
	private OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
		return new OptimisticLockerInnerInterceptor();
	}
}
