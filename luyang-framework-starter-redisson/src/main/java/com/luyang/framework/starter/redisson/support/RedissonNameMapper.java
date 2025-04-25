package com.luyang.framework.starter.redisson.support;

import cn.hutool.core.util.StrUtil;
import org.redisson.api.NameMapper;

import java.util.Optional;

/**
 * Redisson 键前缀映射器，用于为 Redisson 客户端操作的缓存键添加前缀
 * 如：将 redis key 设置为：prefix:name
 *
 * @author yang.lu
 */
public class RedissonNameMapper implements NameMapper {

	private final String prefix;

	/**
	 * 构造方法，初始化前缀。
	 *
	 * @param prefix 要添加的前缀
	 * @author yang.lu
	 */
	public RedissonNameMapper(String prefix) {
		// 如果前缀为空，则使用application.name初始化为空字符串，否则添加冒号作为分隔符
		this.prefix = prefix + StrUtil.COLON;
	}

	/**
	 * 将 Redis 键映射为带有前缀的键
	 *
	 * @param name 原始键
	 * @return 映射后的带有前缀的键
	 * @author yang.lu
	 */
	@Override
	public String map(String name) {
		return Optional.ofNullable(name)
			.filter(StrUtil::isNotBlank)
			.map(n -> StrUtil.startWith(n, prefix) ? n : prefix + n)
			.orElse(null);
	}

	/**
	 * 将带有前缀的键还原为原始键
	 *
	 * @param name 带有前缀的键
	 * @return 原始键
	 * @author yang.lu
	 */
	@Override
	public String unmap(String name) {
		return Optional.ofNullable(name)
			.filter(StrUtil::isNotBlank)
			.map(n -> StrUtil.removePrefix(n, prefix))
			.orElse(null);
	}
}
