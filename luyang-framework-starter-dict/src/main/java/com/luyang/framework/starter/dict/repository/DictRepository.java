package com.luyang.framework.starter.dict.repository;

import com.luyang.framework.starter.dict.model.DictItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author yang.lu
 */
public class DictRepository {

	private final JdbcTemplate jdbcTemplate;

	public DictRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<DictItem> findItems(String dictCode) {
		String sql = """
				SELECT
					id,
					dict_code,
					item_name,
					item_value,
					sort_order,
					remark
				FROM
					system_dict_item
				WHERE
					dict_code = ?
				ORDER BY
					sort_order ASC
			""";
		return jdbcTemplate.query(sql, new DictItemRowMapper(), dictCode);
	}

	public void insertItem(DictItem dictItem) {
		String sql = """
				INSERT INTO system_dict_item
					(dict_code, item_name, item_value, sort_order, remark )
				VALUES
					 (?, ?, ?, ?, ?)
			""";
		jdbcTemplate.update(sql, dictItem.getDictCode(),
			dictItem.getItemName(),
			dictItem.getItemValue(),
			dictItem.getSortOrder(),
			dictItem.getRemark());
	}

	private static class DictItemRowMapper implements RowMapper<DictItem> {
		@Override
		public DictItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			DictItem dictItem = new DictItem();
			dictItem.setId(String.valueOf(rs.getLong("id")));
			dictItem.setDictCode(rs.getString("dict_code"));
			dictItem.setItemName(rs.getString("item_name"));
			dictItem.setItemValue(rs.getString("item_value"));
			dictItem.setSortOrder(rs.getInt("sort_order"));
			dictItem.setRemark(rs.getString("remark"));
			return dictItem;
		}
	}
}
