package io.github.luyang.base.util;

import java.util.Map;

/**
 * Map 工具类
 *
 * @author yang.lu
 */
public class MapUtil {

	private MapUtil() {
	}

	/**
	 * 检查 Map 是否为空（null 或空 Map）
	 *
	 * @param map 待检查的 Map
	 * @return 如果 Map 为空则返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return null == map || map.isEmpty();
	}

	/**
	 * 检查 Map 是否不为空（非 null 且不为空 Map）
	 *
	 * @param map 待检查的 Map
	 * @return 如果 Map 不为空则返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}
}
