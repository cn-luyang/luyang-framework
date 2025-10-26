package io.github.luyang.base.util;

import io.github.luyang.base.util.text.StrFormatter;
import io.github.luyang.base.util.text.StrPool;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author yang.lu
 */
public class StrUtil implements StrPool {

	/**
	 * 字符串常量：{@code "null"}（注意："null" ≠ null）
	 */
	public static final String NULL = "null";

	/**
	 * 字符串常量：空字符串 {@code ""}
	 */
	public static final String EMPTY = "";

	/**
	 * 将CharSequence转换为String，null安全
	 *
	 * @param cs CharSequence对象
	 * @return 字符串，如果输入为null则返回null
	 * @author yang.lu
	 */
	public static String str(CharSequence cs) {
		return cs == null ? null : cs.toString();
	}

	/**
	 * 将空白字符串转换为默认值
	 *
	 * @param str        待检查字符串
	 * @param defaultStr 默认字符串
	 * @return 如果字符串为空白则返回默认值，否则返回原字符串
	 * @author yang.lu
	 */
	public static String blankToDefault(CharSequence str, String defaultStr) {
		return isBlank(str) ? defaultStr : str.toString();
	}

	/**
	 * 格式化字符串
	 *
	 * @param template 模板字符串
	 * @param params   参数列表
	 * @return 格式化后的字符串
	 * @author yang.lu
	 */
	public static String format(CharSequence template, Object... params) {
		if (null == template) {
			return NULL;
		}
		if (ArrayUtil.isEmpty(params) || isBlank(template)) {
			return template.toString();
		}
		return StrFormatter.format(template.toString(), params);
	}

	/**
	 * 检查字符串是否为空
	 *
	 * @param cs 待检查字符串
	 * @return 如果字符串为null或空返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.isEmpty();
	}

	/**
	 * 检查字符串是否为空白
	 *
	 * @param cs 待检查字符串
	 * @return 如果字符串为null、空或全为空白字符返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isBlank(CharSequence cs) {
		return length(cs) == 0 ||
			cs.chars().allMatch(Character::isWhitespace);
	}

	/**
	 * 检查字符串是否非空
	 *
	 * @param cs 待检查字符串
	 * @return 如果字符串不为null且不为空返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isNotEmpty(CharSequence cs) {
		return !isEmpty(cs);
	}

	/**
	 * 检查字符串是否非空白
	 *
	 * @param cs 待检查字符串
	 * @return 如果字符串不为null、不为空且不全为空白字符返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isNotBlank(CharSequence cs) {
		return !isBlank(cs);
	}

	/**
	 * 获取字符串长度
	 *
	 * @param cs 字符串
	 * @return 字符串长度，如果为null返回0
	 * @author yang.lu
	 */
	public static int length(CharSequence cs) {
		return cs == null ? 0 : cs.length();
	}

	/**
	 * 将对象转换为UTF-8字符串
	 *
	 * @param obj 待转换对象
	 * @return UTF-8编码的字符串
	 * @author yang.lu
	 */
	public static String utf8Str(Object obj) {
		return str(obj, CharsetUtil.CHARSET_UTF_8);
	}

	/**
	 * 将对象转换为指定字符集的字符串
	 *
	 * @param obj     待转换对象
	 * @param charset 字符集
	 * @return 转换后的字符串
	 * @author yang.lu
	 */
	public static String str(Object obj, Charset charset) {
		if (obj == null) {
			return null;
		}

		return switch (obj) {
			case String str -> str;
			case byte[] bytes -> str(bytes, charset);
			case Byte[] bytes -> str(bytes, charset);
			case ByteBuffer buffer -> str(buffer, charset);
			default -> ArrayUtil.isArray(obj) ? ArrayUtil.toString(obj) : obj.toString();
		};
	}

	/**
	 * 检查字符串是否以指定字符开头
	 *
	 * @param str 待检查字符串
	 * @param c   起始字符
	 * @return 如果字符串以指定字符开头返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean startWith(CharSequence str, char c) {
		return isNotEmpty(str) && c == str.charAt(0);
	}

	/**
	 * 是否以指定字符串开头
	 *
	 * @param str    被监测字符串
	 * @param prefix 开头字符串
	 * @return 是否以指定字符串开头
	 */
	public static boolean startWith(CharSequence str, CharSequence prefix) {
		return startWith(str, prefix, false);
	}

	public static boolean startWith(CharSequence str, CharSequence prefix, boolean ignoreCase) {
		return startWith(str, prefix, ignoreCase, false);
	}

	public static boolean startWith(CharSequence str, CharSequence prefix, boolean ignoreCase, boolean ignoreEquals) {
		if (null == str || null == prefix) {
			if (ignoreEquals) {
				return false;
			}
			return null == str && null == prefix;
		}

		boolean isStartWith = str.toString()
			.regionMatches(ignoreCase, 0, prefix.toString(), 0, prefix.length());

		if (isStartWith) {
			return (false == ignoreEquals) || (false == equals(str, prefix, ignoreCase));
		}
		return false;
	}

	public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
		if (null == str1) {
			// 只有两个都为null才判断相等
			return str2 == null;
		}
		if (null == str2) {
			// 字符串2空，字符串1非空，直接false
			return false;
		}

		if (ignoreCase) {
			return str1.toString().equalsIgnoreCase(str2.toString());
		} else {
			return str1.toString().contentEquals(str2);
		}
	}

	/**
	 * 将对象转换为字符串，如果为null则返回null
	 *
	 * @param obj 待转换对象
	 * @return 对象的字符串表示或null
	 * @author yang.lu
	 */
	public static String toStringOrNull(Object obj) {
		return obj == null ? null : obj.toString();
	}

	/**
	 * 将对象转换为字符串，如果为null则返回空字符串
	 *
	 * @param obj 待转换对象
	 * @return 对象的字符串表示或空字符串
	 * @author yang.lu
	 */
	public static String toStringOrEmpty(Object obj) {
		return obj == null ? EMPTY : obj.toString();
	}

	/**
	 * 去掉指定前缀
	 *
	 * @param str    字符串
	 * @param prefix 前缀
	 * @return 切掉后的字符串，若前缀不是 prefix，返回原字符串
	 * @author yang.lu
	 */
	public static String removePrefix(CharSequence str, CharSequence prefix) {
		if (isEmpty(str) || isEmpty(prefix)) {
			return str(str);
		}

		final String str2 = str.toString();
		final String prefixStr = prefix.toString();

		if (str2.startsWith(prefixStr)) {
			return subSuf(str2, prefixStr.length()); // 截取后半段
		}
		return str2;
	}

	/**
	 * 去掉指定前缀，忽略大小写
	 *
	 * @param str    字符串
	 * @param prefix 前缀
	 * @return 切掉后的字符串，若前缀不是 prefix，返回原字符串
	 * @author yang.lu
	 */
	public static String removePrefixIgnoreCase(CharSequence str, CharSequence prefix) {
		if (isEmpty(str) || isEmpty(prefix)) {
			return str(str);
		}

		final String str2 = str.toString();
		final String prefixStr = prefix.toString();

		if (startsWithIgnoreCase(str2, prefixStr)) {
			return subSuf(str2, prefixStr.length());
		}
		return str2;
	}

	/**
	 * 检查字符串是否以指定前缀开头，忽略大小写
	 *
	 * @param str    字符串
	 * @param prefix 前缀
	 * @return 如果以指定前缀开头返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
		if (str == null || prefix == null) {
			return false;
		}

		String str2 = str.toString();
		String prefixStr = prefix.toString();

		if (str2.length() < prefixStr.length()) {
			return false;
		}

		return str2.regionMatches(true, 0, prefixStr, 0, prefixStr.length());
	}

	/**
	 * 截取字符串后缀（从指定位置到字符串末尾）
	 *
	 * @param string    原始字符串
	 * @param fromIndex 开始位置（包含）
	 * @return 截取后的字符串，如果输入为空返回null
	 * @author yang.lu
	 */
	public static String subSuf(CharSequence string, int fromIndex) {
		if (isEmpty(string)) {
			return null;
		}
		return sub(string, fromIndex, string.length());
	}

	/**
	 * 安全截取字符串
	 *
	 * @param str              原始字符串
	 * @param fromIndexInclude 开始位置（包含）
	 * @param toIndexExclude   结束位置（不包含）
	 * @return 截取后的字符串
	 * @author yang.lu
	 */
	public static String sub(CharSequence str, int fromIndexInclude, int toIndexExclude) {
		if (isEmpty(str)) {
			return str(str);
		}

		int len = str.length();

		// 规范化开始位置（支持负数索引）
		fromIndexInclude = normalizeIndex(fromIndexInclude, len);
		// 规范化结束位置（支持负数索引）
		toIndexExclude = normalizeIndex(toIndexExclude, len);

		// 确保开始位置不大于结束位置
		if (fromIndexInclude > toIndexExclude) {
			int temp = fromIndexInclude;
			fromIndexInclude = toIndexExclude;
			toIndexExclude = temp;
		}

		// 如果开始位置等于结束位置，返回空字符串
		if (fromIndexInclude == toIndexExclude) {
			return EMPTY;
		}

		return str.toString().substring(fromIndexInclude, toIndexExclude);
	}

	/**
	 * 规范化索引位置，支持负数索引
	 *
	 * @param index 原始索引
	 * @param len   字符串长度
	 * @return 规范化后的索引
	 * @author yang.lu
	 */
	private static int normalizeIndex(int index, int len) {
		if (index < 0) {
			// 负数索引：从末尾开始计算
			index = len + index;
			if (index < 0) {
				index = 0;
			}
		} else if (index > len) {
			// 超出范围：设置为字符串长度
			index = len;
		}
		return index;
	}

	/**
	 * 替换指定字符串的指定区间内字符为"*"
	 * 俗称：脱敏功能，后面其他功能，可以见：DesensitizedUtil(脱敏工具类)
	 *
	 * <pre>
	 * CharSequenceUtil.hide(null,*,*)=null
	 * CharSequenceUtil.hide("",0,*)=""
	 * CharSequenceUtil.hide("jackduan@163.com",-1,4)   ****duan@163.com
	 * CharSequenceUtil.hide("jackduan@163.com",2,3)    ja*kduan@163.com
	 * CharSequenceUtil.hide("jackduan@163.com",3,2)    jackduan@163.com
	 * CharSequenceUtil.hide("jackduan@163.com",16,16)  jackduan@163.com
	 * CharSequenceUtil.hide("jackduan@163.com",16,17)  jackduan@163.com
	 * </pre>
	 *
	 * @param str          字符串
	 * @param startInclude 开始位置（包含）
	 * @param endExclude   结束位置（不包含）
	 * @return 替换后的字符串
	 * @author yang.lu
	 */
	public static String hide(CharSequence str, int startInclude, int endExclude) {
		return replaceByCodePoint(str, startInclude, endExclude, '*');
	}

	/**
	 * 替换指定字符串的指定区间内字符为固定字符<br>
	 * 此方法使用{@link String#codePoints()}完成拆分替换
	 *
	 * @param str          字符串
	 * @param startInclude 开始位置（包含）
	 * @param endExclude   结束位置（不包含）
	 * @param replacedChar 被替换的字符
	 * @return 替换后的字符串
	 * @author yang.lu
	 */
	public static String replaceByCodePoint(CharSequence str, int startInclude, int endExclude, char replacedChar) {
		if (isEmpty(str)) {
			return str(str);
		}
		final String originalStr = str(str);
		int[] strCodePoints = originalStr.codePoints().toArray();
		final int strLength = strCodePoints.length;
		if (startInclude > strLength) {
			return originalStr;
		}
		if (endExclude > strLength) {
			endExclude = strLength;
		}
		if (startInclude > endExclude) {
			// 如果起始位置大于结束位置，不替换
			return originalStr;
		}

		final StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < strLength; i++) {
			if (i >= startInclude && i < endExclude) {
				stringBuilder.append(replacedChar);
			} else {
				stringBuilder.append(new String(strCodePoints, i, 1));
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * 切分字符串，去空白项，返回不可变 List。
	 *
	 * @param str       原字符串，允许 null
	 * @param delimiter 分隔符，支持正则元字符（如 "."、"|"、"+"）
	 * @return 非 null 的 List，空输入返回空 List
	 * @author yang.lu
	 */
	public static List<String> splitTrim(String str, String delimiter) {
		if (str == null || str.isEmpty()) {
			return Collections.emptyList();
		}
		// 如果分隔符是正则元字符，先 quote
		final String regex = "\\s*" + Pattern.quote(delimiter) + "\\s*";
		return Arrays.stream(str.split(regex))
			.filter(s -> !s.trim().isEmpty())
			.collect(Collectors.toList());
	}
}
