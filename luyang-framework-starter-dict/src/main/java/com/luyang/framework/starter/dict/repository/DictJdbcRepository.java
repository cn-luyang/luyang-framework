package com.luyang.framework.starter.dict.repository;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author yang.lu
 */
public class DictJdbcRepository {

	private final JdbcTemplate jdbcTemplate;

	public DictJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
