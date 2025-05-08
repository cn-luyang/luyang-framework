package com.luyang.framework.starter.dict.config;

import com.luyang.framework.starter.dict.controller.DictController;
import com.luyang.framework.starter.dict.repository.DictRepository;
import com.luyang.framework.starter.dict.service.DictService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author yang.lu
 */
@Configuration
public class DictAutoConfiguration {

	@Bean
	public DictRepository dictRepository(JdbcTemplate jdbcTemplate) {
		return new DictRepository(jdbcTemplate);
	}

//	@Bean
//	public DictCache dictCache(RedissonClient redissonClient) {
//		return new DictCache(redissonClient);
//	}

	@Bean
	public DictService dictService(/*DictCache dictCache, */DictRepository dictRepository) {
		return new DictService(/*dictCache, */dictRepository);
	}

	@Bean
	public DictController dictController(DictService dictService) {
		return new DictController(dictService);
	}
}
