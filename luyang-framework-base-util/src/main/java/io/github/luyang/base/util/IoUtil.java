package io.github.luyang.base.util;

import java.io.Closeable;

/**
 * IO 工具类
 *
 * @author yang.lu
 */
public final class IoUtil {

	private IoUtil() {}

	/**
	 * 静默安全关闭资源，自动处理空值和异常
	 *
	 * @param closeable 要关闭的资源对象
	 * @author yang.lu
	 */
	public static void close(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (Exception e) {
				// 静默关闭，忽略异常
			}
		}
	}
}
