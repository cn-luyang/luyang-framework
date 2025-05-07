package com.luyang.framework.starter.dict.repository;

import com.luyang.framework.starter.dict.entity.DictItemEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yang.lu
 */
@Repository
public class DictRepository {

	private final JdbcTemplate jdbcTemplate;

	private static final String DICT_ITEM_TABLE_NAME = "system_dict_item";
	private static final String DICT_ITEM_COLUMN_NAMES = """
				  id,
				  dict_code,
				  item_name,
				  item_value,
				  sort_order,
				  remark
		""";

	public DictRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<DictItemEntity> findByDictCode(String dictCode) {
		String sql = """
			SELECT %s FROM %s WHERE dict_code = ? ORDER BY item_sort ASC
			""".formatted(DICT_ITEM_COLUMN_NAMES, DICT_ITEM_TABLE_NAME);
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(DictItemEntity.class), dictCode);
	}
}
