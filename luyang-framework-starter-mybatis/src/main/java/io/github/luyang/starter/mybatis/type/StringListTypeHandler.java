package io.github.luyang.starter.mybatis.type;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 自定义类型处理器，用于将字符串列表与数据库中的 VARCHAR 字段进行映射
 *
 * @author yang.lu
 */
// 映射到 JDBC VARCHAR 类型
@MappedJdbcTypes(JdbcType.VARCHAR)
// 映射到 List<String> 类型
@MappedTypes(List.class)
public class StringListTypeHandler implements TypeHandler<List<String>> {

	@Override
	public void setParameter(PreparedStatement ps, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
		// 将字符串列表以逗号连接并设置到 PreparedStatement
		ps.setString(i, CollUtil.join(strings, StrPool.COMMA));
	}

	@Override
	public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
		// 从 ResultSet 中获取字符串并解析为列表
		String value = rs.getString(columnName);
		return parseResult(value);
	}

	@Override
	public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
		// 根据列索引获取字符串并解析为列表
		String value = rs.getString(columnIndex);
		return parseResult(value);
	}

	@Override
	public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// 从 CallableStatement 中获取字符串并解析为列表
		String value = cs.getString(columnIndex);
		return parseResult(value);
	}

	/**
	 * 解析字符串，将其拆分为字符串列表
	 *
	 * @param value 输入字符串
	 * @return List<String> 拆分后的字符串列表，如果输入为空则返回 null
	 * @author yang.lu
	 */
	private List<String> parseResult(String value) {
		return StrUtil.isBlank(value)
			? null
			// 拆分并去除空白
			: StrUtil.splitTrim(value, StrPool.COMMA);
	}
}
