package io.github.luyang.starter.dict.repository;

import io.github.luyang.starter.dict.model.Dict;
import io.github.luyang.starter.dict.model.DictItem;
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

    public int insertDict(Dict dict) {
        String sql = """
                insert into system_dict
                    (dict_code, dict_name, remark )
                values
                     (?, ?, ?)
            """;
        return jdbcTemplate.update(sql, dict.getDictCode(),
            dict.getDictName(),
            dict.getRemark());
    }

    public boolean existsDict(String dictCode) {
        String sql = "select count(1) from system_dict where dict_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, dictCode);
        return count != null && count > 0;
    }

    /**
     * 查找字典项列表
     *
     * @param dictCode 字典码
     * @return 字典项列表
     * @author yang.lu
     */
    public List<DictItem> findItems(String dictCode) {
        String sql = """
                select
                    id,
                    dict_code,
                    item_name,
                    item_value,
                    sort_order,
                    remark
                from
                    system_dict_item
                where
                    dict_code = ?
                order by
                    sort_order asc
            """;
        return jdbcTemplate.query(sql, new DictItemRowMapper(), dictCode);
    }

    /**
     * 添加字典项
     *
     * @param dictItem 字典项
     * @return 操作条数
     * @author yang.lu
     */
    public int insertItem(DictItem dictItem) {
        String sql = """
                insert into system_dict_item
                    (dict_code, item_name, item_value, sort_order, remark )
                values
                     (?, ?, ?, ?, ?)
            """;
        return jdbcTemplate.update(sql, dictItem.getDictCode(),
            dictItem.getItemName(),
            dictItem.getItemValue(),
            dictItem.getSortOrder(),
            dictItem.getRemark());
    }

    /**
     * 查找字典项是否存在
     *
     * @param dictCode  字典码
     * @param itemValue 字典值
     * @return true存在 false不存在
     * @author yang.lu
     */
    public boolean existsItem(String dictCode, String itemValue) {
        String sql = "select count(1) from system_dict_item where dict_code = ? and item_value = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, dictCode, itemValue);
        return count != null && count > 0;
    }

    /**
     * 删除字典项
     *
     * @param dictCode  字典码
     * @param itemValue 字典值
     * @return true删除 false删除失败
     * @author yang.lu
     */
    public boolean removeItem(String dictCode, String itemValue) {
        String sql = "delete from system_dict_item where dict_code = ? and item_value = ?";
        int count = jdbcTemplate.update(sql, Integer.class, dictCode, itemValue);
        return count > 0;
    }

    /**
     * 数据类型转换
     *
     * @author yang.lu
     */
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
