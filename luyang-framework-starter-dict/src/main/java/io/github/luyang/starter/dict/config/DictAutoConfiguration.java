package io.github.luyang.starter.dict.config;

import io.github.luyang.starter.dict.cache.DictCache;
import io.github.luyang.starter.dict.controller.DictController;
import io.github.luyang.starter.dict.repository.DictRepository;
import io.github.luyang.starter.dict.service.DictService;
import org.redisson.api.RedissonClient;
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

	@Bean
	public DictCache dictCache(RedissonClient redissonClient) {
		return new DictCache(redissonClient);
	}

	@Bean
	public DictService dictService(DictCache dictCache, DictRepository dictRepository) {
		return new DictService(dictCache, dictRepository);
	}

	@Bean
	public DictController dictController(DictService dictService) {
		return new DictController(dictService);
	}
}
