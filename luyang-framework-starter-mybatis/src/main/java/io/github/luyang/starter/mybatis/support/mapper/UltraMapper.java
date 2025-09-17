package io.github.luyang.starter.mybatis.support.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;

import java.util.List;

/**
 * 扩展 BaseMapper
 *
 * @author yang.lu
 */
public interface UltraMapper<T> extends BaseMapper<T> {


	/**
	 * 创建 Lambda 风格的查询链式构造器
	 *
	 * @return LambdaQueryChainWrapper 对象，用于构建查询条件
	 * @author yang.lu
	 */
	default LambdaQueryChainWrapper<T> lambdaQuery() {
		return ChainWrappers.lambdaQueryChain(this);
	}

	/**
	 * 创建普通查询链式构造器
	 *
	 * @return QueryChainWrapper 对象，用于构建查询条件
	 * @author yang.lu
	 */
	default QueryChainWrapper<T> query() {
		return ChainWrappers.queryChain(this);
	}

	/**
	 * 创建 Lambda 风格的更新链式构造器
	 *
	 * @return LambdaUpdateChainWrapper 对象，用于构建更新条件
	 * @author yang.lu
	 */
	default LambdaUpdateChainWrapper<T> lambdaUpdate() {
		return ChainWrappers.lambdaUpdateChain(this);
	}

	/**
	 * 创建普通更新链式构造器
	 *
	 * @return UpdateChainWrapper 对象，用于构建更新条件
	 * @author yang.lu
	 */
	default UpdateChainWrapper<T> update() {
		return ChainWrappers.updateChain(this);
	}

	/**
	 * 根据指定字段查询一条数据
	 *
	 * @param field 字段 Lambda 表达式，如 UserEntity::getUsername
	 * @param value 字段值
	 * @return T 匹配的实体对象，若不存在则返回 null
	 * @author yang.lu
	 */
	default T selectOne(SFunction<T, ?> field, Object value) {
		return this.lambdaQuery().eq(field, value).one();
	}

	/**
	 * 根据指定字段统计匹配数量
	 *
	 * @param field 字段 Lambda 表达式
	 * @param value 字段值
	 * @param <R>   字段类型
	 * @return 匹配的数据条数
	 * @author yang.lu
	 */
	default <R> Long selectCount(SFunction<T, R> field, R value) {
		return this.lambdaQuery().eq(field, value).count();
	}

	/**
	 * 根据指定字段删除数据
	 *
	 * @param field 字段 Lambda 表达式
	 * @param value 字段值
	 * @param <R>   字段类型
	 * @return 是否成功删除，true 表示删除成功
	 * @author yang.lu
	 */
	default <R> boolean delete(SFunction<T, R> field, R value) {
		return this.lambdaUpdate().eq(field, value).remove();
	}

	/**
	 * 判断指定字段值是否存在数据
	 *
	 * @param field 字段 Lambda 表达式
	 * @param value 字段值
	 * @param <R>   字段类型
	 * @return 是否存在匹配数据，true 表示存在
	 * @author yang.lu
	 */
	default <R> boolean exists(SFunction<T, R> field, R value) {
		return this.lambdaQuery().eq(field, value).exists();
	}

	/**
	 * 判断指定字段值是否不存在数据
	 *
	 * @param field 字段 Lambda 表达式
	 * @param value 字段值
	 * @param <R>   字段类型
	 * @return 是否不存在匹配数据，true 表示不存在
	 * @author yang.lu
	 */
	default <R> boolean notExists(SFunction<T, R> field, R value) {
		return !exists(field, value);
	}

	/**
	 * 根据指定字段查询匹配数据列表。
	 *
	 * @param field 字段 Lambda 表达式
	 * @param value 字段值
	 * @param <R>   字段类型
	 * @return 匹配的数据列表
	 * @author yang.lu
	 */
	default <R> List<T> listByField(SFunction<T, R> field, R value) {
		return lambdaQuery().eq(field, value).list();
	}
}

