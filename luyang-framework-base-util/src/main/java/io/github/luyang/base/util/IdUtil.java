package io.github.luyang.base.util;

import java.util.UUID;

/**
 * @author yang.lu
 */
public final class IdUtil {

	private IdUtil() {

	}

	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	public static String simpleUUID() {
		return randomUUID().replace("-", "");
	}
}
