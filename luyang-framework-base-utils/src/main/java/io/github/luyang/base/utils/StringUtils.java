package io.github.luyang.base.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 字符串工具类
 *
 * @author wangjixin
 */
public final class StringUtils {

	private StringUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	// ================== 空值判断 ================== //

	/**
	 * 判断字符序列是否为空
	 *
	 * @param cs 要判断的字符序列
	 * @return 如果字符序列为 null 或者长度为 0，返回 true；否则返回 false
	 */
	public static boolean isEmpty(CharSequence cs) {
		return null == cs || cs.isEmpty();
	}

	/**
	 * 判断字符序列是否不为空
	 *
	 * @param cs 要判断的字符序列
	 * @return 如果字符序列不为 null 且长度大于 0，返回 true；否则返回 false
	 */
	public static boolean isNotEmpty(CharSequence cs) {
		return !isEmpty(cs);
	}

	/**
	 * 判断字符序列是否为空白（即 null 或者仅包含空白字符）
	 *
	 * @param cs 要判断的字符序列
	 * @return 如果字符序列为 null 或者仅包含空白字符，返回 true；否则返回 false
	 */
	public static boolean isBlank(CharSequence cs) {
		if (null == cs) {
			return true;
		}
		for (int i = 0; i < cs.length(); i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符序列是否不为空白
	 *
	 * @param cs 要判断的字符序列
	 * @return 如果字符序列不为 null 且不只包含空白字符，返回 true；否则返回 false
	 */
	public static boolean isNotBlank(CharSequence cs) {
		return !isBlank(cs);
	}

	// ================== 字符串处理 ================== //

	/**
	 * 去除字符串首尾的空白字符
	 *
	 * @param str 要处理的字符串
	 * @return 如果字符串为 null，返回 null；否则返回去除首尾空白字符后的字符串
	 */
	public static String trim(String str) {
		return null == str ? null : str.trim();
	}

	/**
	 * 去除字符串首尾的空白字符，如果字符串为 null 则返回空字符串
	 *
	 * @param str 要处理的字符串
	 * @return 如果字符串为 null，返回空字符串；否则返回去除首尾空白字符后的字符串
	 */
	public static String trimToEmpty(String str) {
		return null == str ? "" : str.trim();
	}

	/**
	 * 去除字符串首尾的空白字符，如果处理后字符串为空则返回 null
	 *
	 * @param str 要处理的字符串
	 * @return 如果字符串为 null 或者处理后为空，返回 null；否则返回去除首尾空白字符后的字符串
	 */
	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	/**
	 * 从指定位置开始截取字符串
	 *
	 * @param str   要截取的字符串
	 * @param start 截取的起始位置
	 * @return 如果字符串为 null，返回 null；否则返回从指定位置开始截取到字符串末尾的子字符串
	 */
	public static String substring(String str, int start) {
		return substring(str, start, str.length());
	}

	/**
	 * 截取字符串的指定部分
	 *
	 * @param str   要截取的字符串
	 * @param start 截取的起始位置（可以为负数，表示从字符串末尾开始计数）
	 * @param end   截取的结束位置（可以为负数，表示从字符串末尾开始计数）
	 * @return 如果字符串为 null，返回 null；否则返回截取的子字符串
	 */
	public static String substring(String str, int start, int end) {
		if (null == str) {
			return null;
		}
		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}
		if (end > str.length()) {
			end = str.length();
		}
		if (start > end) {
			return "";
		}
		return str.substring(start, end);
	}

	/**
	 * 反转字符串
	 *
	 * @param str 要反转的字符串
	 * @return 如果字符串为 null，返回 null；否则返回反转后的字符串
	 */
	public static String reverse(String str) {
		return null == str ? null : new StringBuilder(str).reverse().toString();
	}

	/**
	 * 替换字符串中的指定内容
	 *
	 * @param text         要进行替换操作的字符串
	 * @param searchString 要查找并替换的字符串
	 * @param replacement  替换的字符串
	 * @return 如果原字符串、查找字符串为空或者替换字符串为 null，返回原字符串；否则返回替换后的字符串
	 */
	public static String replace(String text, String searchString, String replacement) {
		if (isEmpty(text) || isEmpty(searchString) || null == replacement) {
			return text;
		}
		return text.replace(searchString, replacement);
	}

	// ================== 字符串生成 ================== //

	private static final Random RANDOM = new Random();
	private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 * 生成指定长度的随机字符串
	 *
	 * @param length 要生成的随机字符串的长度
	 * @return 生成的随机字符串
	 */
	public static String random(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(RANDOM_STR.charAt(RANDOM.nextInt(RANDOM_STR.length())));
		}
		return sb.toString();
	}

	// ================== 格式转换 ================== //

	/**
	 * 将下划线命名的字符串转换为驼峰命名
	 *
	 * @param str 要转换的字符串
	 * @return 如果字符串为空白，返回原字符串；否则返回转换为驼峰命名后的字符串
	 */
	public static String toCamelCase(String str) {
		if (isBlank(str)) {
			return str;
		}
		str = str.toLowerCase();
		StringBuilder sb = new StringBuilder(str.length());
		boolean upperCase = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '_') {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 将驼峰命名的字符串转换为下划线命名
	 *
	 * @param str 要转换的字符串
	 * @return 如果字符串为空白，返回原字符串；否则返回转换为下划线命名后的字符串
	 */
	public static String toSnakeCase(String str) {
		if (isBlank(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append('_').append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	// ================== 加密处理 ================== //

	/**
	 * 对输入字符串进行 MD5 加密
	 *
	 * @param input 要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String md5(String input) {
		return hash(input, "MD5");
	}

	/**
	 * 对输入字符串进行 SHA-256 加密
	 *
	 * @param input 要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String sha256(String input) {
		return hash(input, "SHA-256");
	}

	/**
	 * 使用指定算法对输入字符串进行哈希加密
	 *
	 * @param input     要加密的字符串
	 * @param algorithm 加密算法（如 "MD5", "SHA-256" 等）
	 * @return 加密后的字符串
	 * @throws RuntimeException 如果指定的算法不可用或者字符编码不支持
	 */
	private static String hash(String input, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Hash计算失败", e);
		}
	}

	// ================== 高级功能 ================== //

	/**
	 * 判断字符序列是否包含任意一个指定的字符串
	 *
	 * @param cs            要检查的字符序列
	 * @param searchStrings 要查找的字符串数组
	 * @return 如果字符序列为空或者查找字符串数组为 null，返回 false；否则如果字符序列包含任意一个查找字符串，返回 true；否则返回 false
	 */
	public static boolean containsAny(CharSequence cs, CharSequence... searchStrings) {
		if (isEmpty(cs) || null == searchStrings) {
			return false;
		}
		for (CharSequence search : searchStrings) {
			if (cs.toString().contains(search)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 使用指定分隔符将可迭代对象中的元素连接成一个字符串
	 *
	 * @param delimiter 分隔符
	 * @param elements  可迭代对象
	 * @return 如果可迭代对象为 null，返回 null；否则返回连接后的字符串
	 */
	public static String join(String delimiter, Iterable<?> elements) {
		if (null == elements) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (Object element : elements) {
			if (null != element) {
				if (!sb.isEmpty()) {
					sb.append(delimiter);
				}
				sb.append(element);
			}
		}
		return sb.toString();
	}
}
