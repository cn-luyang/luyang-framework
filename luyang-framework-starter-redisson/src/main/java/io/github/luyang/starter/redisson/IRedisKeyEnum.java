package io.github.luyang.starter.redisson;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * Redis 键名规范化枚举接口
 *
 * @author yang.lu
 */
public interface IRedisKeyEnum extends Serializable {

	/**
	 * 获取 Key 前缀
	 */
	String getPrefix();

	/**
	 * 获取过期时间（单位：秒）
	 */
	long getExpireSeconds();

	/**
	 * 获取业务描述
	 */
	String getDesc();

	/**
	 * 构建完整的 Redis Key
	 *
	 * @param args 动态占位参数（将以冒号分割拼接）
	 * @return 完整 Key，例如 "prefix:arg1:arg2"
	 */
	default String buildKey(Object... args) {
		if (null == args || args.length == 0) {
			return getPrefix();
		}

		if (args.length == 1) {
			return getPrefix() + ":" + args[0];
		}

		StringJoiner joiner = new StringJoiner(":");
		joiner.add(getPrefix());
		for (Object arg : args) {
			joiner.add(String.valueOf(arg));
		}

		return joiner.toString();
	}
}
