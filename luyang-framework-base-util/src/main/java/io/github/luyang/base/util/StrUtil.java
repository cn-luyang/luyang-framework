package io.github.luyang.base.util;

import io.github.luyang.base.util.text.StrPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author yang.lu
 */
public class StrUtil implements StrPool {

//	private StrUtil() {
//		throw new UnsupportedOperationException("StrUtil is a utility class and cannot be instantiated");
//	}

	public static String str(CharSequence cs) {
		return null == cs ? null : cs.toString();
	}

	/**
	 * 检查字符序列是否为 null 或空字符串 ""
	 *
	 * @param cs 要检查的字符序列
	 * @return 如果为空返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isEmpty(CharSequence cs) {
		return null == cs || cs.isEmpty();
	}

	/**
	 * 是否包含空字符串
	 * <pre>
	 * 		StrUtil.hasEmpty()                  = true
	 * 		StrUtil.hasEmpty("", null)          = true
	 *      StrUtil.hasEmpty("123", "")         = true
	 *      StrUtil.hasEmpty("123", "abc")      = false
	 *      StrUtil.hasEmpty(" ", "\t", "\n")   = false
	 * </pre>
	 *
	 * @param css 字符串列表
	 * @return 是否包含空字符串
	 * @author yang.lu
	 */
	public static boolean hasEmpty(CharSequence... css) {
		if (ArrayUtil.isEmpty(css)) {
			return true;
		}

		for (CharSequence str : css) {
			if (isEmpty(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查字符序列是否为空白，null 或 空字符串"" 或 全空白字符
	 *
	 * @param cs 要检查的字符序列
	 * @return 如果为空返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isBlank(CharSequence cs) {
		return length(cs) == 0 || cs.chars().allMatch(Character::isWhitespace);
	}

	/**
	 * 指定字符串数组中，是否包含空字符串
	 * <pre>
	 *     StrUtil.hasBlank() 					= true
	 *     StrUtil.hasBlank("", null, " ") 		= true
	 *     StrUtil.hasBlank("123", " ")     	= true
	 *     StrUtil.hasBlank("123", "abc")   	= false
	 * </pre>
	 *
	 * @param css 字符串列表
	 * @return 是否包含空字符串
	 * @author yang.lu
	 */
	public static boolean hasBlank(CharSequence... css) {
		if (ArrayUtil.isEmpty(css)) {
			return true;
		}

		for (CharSequence str : css) {
			if (isBlank(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查字符序列是否非空
	 *
	 * @param cs 要检查的字符序列
	 * @return 如果非空返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isNotEmpty(CharSequence cs) {
		return !isEmpty(cs);
	}

	/**
	 * 检查字符序列是否非空白
	 *
	 * @param cs 要检查的字符序列
	 * @return 如果非空白返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean isNotBlank(CharSequence cs) {
		return !isBlank(cs);
	}

	/**
	 * 获取字符序列长度，null安全
	 *
	 * @param cs 字符序列
	 * @return 字符序列长度，null返回0
	 * @author yang.lu
	 */
	public static int length(CharSequence cs) {
		return null == cs ? 0 : cs.length();
	}

	/**
	 * 如果字符串为空白则返回默认值，否则返回原字符串
	 *
	 * @param str        要检查的字符串
	 * @param defaultStr 默认值
	 * @return 处理后的字符串
	 * @author yang.lu
	 */
	public static String blankToDefault(CharSequence str, String defaultStr) {
		return isBlank(str) ? defaultStr : str.toString();
	}

	/**
	 * 如果字符串为空则返回默认值，否则返回原字符串
	 *
	 * @param str        要检查的字符串
	 * @param defaultStr 默认值
	 * @return 处理后的字符串
	 * @author yang.lu
	 */
	public static String emptyToDefault(CharSequence str, String defaultStr) {
		return isEmpty(str) ? defaultStr : str.toString();
	}

	/**
	 * 对象转字符串，null安全
	 *
	 * @param obj 要转换的对象
	 * @return 对象的字符串表示，null返回null
	 * @author yang.lu
	 */
	public static String toStringOrNull(Object obj) {
		return null == obj ? null : obj.toString();
	}

	/**
	 * 截取字符串从指定位置到末尾
	 *
	 * @param str   原字符串
	 * @param start 起始位置（包含）
	 * @return 截取后的子串
	 * @author yang.lu
	 */
	public static String sub(final String str, int start) {
		return sub(str, start, str.length());
	}

	/**
	 * 截取字符串的指定区间
	 * <pre>
	 *     StrUtil.sub("abcde", 1, 4)   	= "bcd"
	 *     StrUtil.sub("abcde", -3, -1) 	= "cd"
	 *     StrUtil.sub("abcde", 1, 10)  	= "bcde"
	 *     StrUtil.sub("abcde", 5, 1)   	= ""    // start > end 返回空
	 * </pre>
	 *
	 * @param str   原字符串
	 * @param start 起始位置（包含）
	 * @param end   结束位置（不包含）
	 * @return 截取后的子串
	 * @author yang.lu
	 */
	public static String sub(String str, int start, int end) {

		if (null == str) {
			return null;
		}

		// 处理负数索引
		if (end < 0) {
			// 记住end是负数
			end = str.length() + end;
		}

		if (start < 0) {
			// 记住start是负数
			start = str.length() + start;
		}

		// 检查长度
		if (end > str.length()) {
			end = str.length();
		}

		// 如果起始位置大于结束位置，返回空字符串
		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}

		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 移除字符串的前缀
	 * <pre>
	 *     StrUtil.removePrefix("testString", "test"); = "String"
	 *     StrUtil.removePrefix("hello", "test");      =  "hello" (前缀不匹配时返回原字符串)
	 * </pre>
	 *
	 * @param str    原字符串
	 * @param prefix 要移除的前缀
	 * @return 移除前缀后的字符串
	 * @author yang.lu
	 */
	public static String removePrefix(String str, CharSequence prefix) {
		if (null != str && startsWith(str, prefix)) {
			return str.substring(length(prefix));
		}
		return str;
	}

	/**
	 * 移除字符串的后缀
	 * <pre>
	 *     StrUtil.removeSuffix("hello.txt", ".txt"); = "hello"
	 *     StrUtil.removeSuffix("hello", ".txt");     = "hello" (后缀不匹配时返回原字符串)
	 * </pre>
	 *
	 * @param str    原字符串
	 * @param suffix 要移除的后缀
	 * @return 移除后缀后的字符串
	 * @author yang.lu
	 */
	public String removeSuffix(final String str, final CharSequence suffix) {
		if (isEmpty(str) || isEmpty(suffix)) {
			return str;
		}
		if (endsWith(str, suffix)) {
			return str.substring(0, str.length() - suffix.length());
		}
		return str;
	}

	/**
	 * 检查字符序列是否以指定前缀开头（区分大小写）
	 *
	 * @param source 源字符序列
	 * @param prefix 前缀
	 * @return 如果以指定前缀开头返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean startsWith(CharSequence source, CharSequence prefix) {
		return startsWith(source, prefix, false);
	}

	/**
	 * 检查字符序列是否以指定前缀开头（忽略大小写）
	 *
	 * @param source 源字符序列
	 * @param prefix 前缀
	 * @return 如果以指定前缀开头返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean startsWithIgnoreCase(CharSequence source, CharSequence prefix) {
		return startsWith(source, prefix, true);
	}

	/**
	 * 检查字符序列是否以指定前缀开头
	 *
	 * @param source     源字符序列
	 * @param prefix     前缀
	 * @param ignoreCase 是否忽略大小写
	 * @return 如果以指定前缀开头返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean startsWith(CharSequence source, CharSequence prefix, boolean ignoreCase) {
		if (null == source || null == prefix) {
			return source == prefix;
		}
		final int prefixLength = prefix.length();
		if (prefixLength > source.length()) {
			return false;
		}

		return regionMatches(source, ignoreCase, 0, prefix, 0, prefixLength);
	}

	/**
	 * 检查字符序列是否以任意一个指定前缀开头（区分大小写）
	 *
	 * @param source         源字符序列
	 * @param searchPrefixes 要检查的前缀数组
	 * @return 如果以任意一个前缀开头返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean startsWithAny(final CharSequence source, final CharSequence... searchPrefixes) {
		if (isEmpty(source) || ArrayUtil.isEmpty(searchPrefixes)) {
			return false;
		}

		for (final CharSequence searchPrefix : searchPrefixes) {
			if (startsWith(source, searchPrefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查字符序列是否以指定后缀结尾（区分大小写）
	 *
	 * @param source 源字符序列
	 * @param suffix 后缀
	 * @return 如果以指定后缀结尾返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean endsWith(CharSequence source, CharSequence suffix) {
		return endsWith(source, suffix, false);
	}

	/**
	 * 检查字符序列是否以指定后缀结尾（忽略大小写）
	 *
	 * @param source 源字符序列
	 * @param suffix 后缀
	 * @return 如果以指定后缀结尾返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean endsWithIgnoreCase(CharSequence source, CharSequence suffix) {
		return endsWith(source, suffix, true);
	}

	/**
	 * 检查字符序列是否以指定后缀结尾
	 *
	 * @param source     源字符序列
	 * @param suffix     后缀
	 * @param ignoreCase 是否忽略大小写
	 * @return 如果以指定后缀结尾返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean endsWith(CharSequence source, CharSequence suffix, boolean ignoreCase) {
		if (source == null || suffix == null) {
			return source == suffix;
		}
		final int suffixLength = suffix.length();
		if (suffixLength > source.length()) {
			return false;
		}
		return regionMatches(source, ignoreCase, source.length() - suffixLength, suffix, 0, suffixLength);
	}

	/**
	 * 检查字符序列是否以任意一个指定后缀结尾（区分大小写）
	 *
	 * @param source         源字符序列
	 * @param searchSuffixes 要检查的后缀数组
	 * @return 如果以任意一个后缀结尾返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean endsWithAny(CharSequence source, CharSequence... searchSuffixes) {
		if (isEmpty(source) || ArrayUtil.isEmpty(searchSuffixes)) {
			return false;
		}

		for (final CharSequence searchSuffix : searchSuffixes) {
			if (endsWith(source, searchSuffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 比较两个字符序列是否相等（区分大小写）
	 *
	 * @param source 第一个字符序列
	 * @param target 第二个字符序列
	 * @return 如果两个字符序列相等（区分大小写）则返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean equals(CharSequence source, CharSequence target) {
		return equals(source, target, false);
	}

	/**
	 * 比较两个字符序列是否相等（不区分大小写）
	 *
	 * @param source 第一个字符序列
	 * @param target 第二个字符序列
	 * @return 如果两个字符序列相等（不区分大小写）则返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean equalsIgnoreCase(CharSequence source, CharSequence target) {
		return equals(source, target, true);
	}

	/**
	 * 比较两个字符序列是否相等
	 * <pre>
	 *     StrUtil.equals("hello", "HELLO", false) = false
	 *     StrUtil.equals("hello", "HELLO", true)  = true
	 * </pre>
	 *
	 * @param source     第一个字符序列
	 * @param target     第二个字符序列
	 * @param ignoreCase 是否忽略大小写比较
	 *                   true：不区分大小写比较
	 *                   false：区分大小写比较
	 * @return 如果两个字符序列相等则返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean equals(CharSequence source, CharSequence target, boolean ignoreCase) {
		// 如果是同一个对象的引用，直接返回true
		if (source == target) {
			return true;
		}

		// 如果任一序列为null（但不同时为null，因为前一步已检查），返回false
		if (source == null || target == null) {
			return false;
		}

		// 长度检查：如果长度不同，肯定不相等
		if (source.length() != target.length()) {
			return false;
		}

		// 忽略大小写比较：使用regionMatches方法进行完整的区域比较
		if (ignoreCase) {
			return regionMatches(source, true, 0, target, 0, source.length());
		}

		// 如果两个字符序列都是String类型，直接使用String.equals()方法
		if (source instanceof String && target instanceof String) {
			return source.equals(target);
		}

		// 区分大小写比较：逐个字符比较
		final int length = source.length();
		for (int i = 0; i < length; i++) {
			if (source.charAt(i) != target.charAt(i)) {
				return false;
			}
		}

		// 所有字符都比较完毕且全部相等，返回true
		return true;
	}

	/**
	 * 检查字符序列是否与多个目标中的任意一个相等（区分大小写）
	 *
	 * @param source  源字符序列
	 * @param targets 多个目标字符序列
	 * @return 如果源字符序列与任意一个目标相等则返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean equalsAny(CharSequence source, CharSequence... targets) {
		return equalsAny(source, false, targets);
	}

	/**
	 * 检查字符序列是否与多个目标中的任意一个相等（不区分大小写）
	 *
	 * @param source  源字符序列
	 * @param targets 多个目标字符序列
	 * @return 如果源字符序列与任意一个目标相等则返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean equalsAnyIgnoreCase(CharSequence source, CharSequence... targets) {
		return equalsAny(source, true, targets);
	}

	/**
	 * 检查字符序列是否与多个目标中的任意一个相等
	 * <pre>
	 *     StrUtil.equalsAny("hello", "hi", "hello", "hey") = true
	 *     StrUtil.equalsAny("HELLO", true, "hi", "hello")  = true (忽略大小写)
	 * </pre>
	 *
	 * @param source     源字符序列
	 * @param ignoreCase 是否忽略大小写
	 * @param targets    多个目标字符序列
	 * @return 如果源字符序列与任意一个目标相等则返回true，否则返回false
	 * @author yang.lu
	 */
	public static boolean equalsAny(CharSequence source, boolean ignoreCase, CharSequence... targets) {
		if (ArrayUtil.isEmpty(targets)) {
			return false;
		}

		for (CharSequence target : targets) {
			if (equals(source, target, ignoreCase)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 按分隔符分割字符串（不保留空部分）
	 * <pre>
	 *     StrUtil.split("a,b,,c", ',')   = ["a", "b", "c"]
	 *     StrUtil.split(",,a,", ',')     = ["a"]
	 *     StrUtil.split("", ',')         = []
	 * </pre>
	 *
	 * @param str       要分割的字符串
	 * @param delimiter 分隔符
	 * @return 分割后的字符串列表
	 * @author yang.lu
	 */
	public static List<String> split(String str, char delimiter) {
		return splitWorker(str, delimiter, false);
	}

	/**
	 * 按分隔符分割字符串，保留所有空部分（包括空部分）
	 * <pre>
	 *     splitKeepEmpty("a,,b,c,", ',')		= ["a", "", "b", "c", ""]
	 *     splitKeepEmpty("", ',')				= [""]
	 *     splitKeepEmpty(",a,b", ',')			= ["", "a", "b"]
	 * </pre>
	 *
	 * @param str       要分割的字符串
	 * @param delimiter 分隔符
	 * @return 分割后的字符串数组，包含所有空部分
	 */
	public static List<String> splitKeepEmpty(String str, char delimiter) {
		return splitWorker(str, delimiter, true);
	}

	/**
	 * 按分隔符分割字符串为数组
	 *
	 * @param str       要分割的字符串
	 * @param delimiter 分隔符
	 * @return 分割后的字符串数组
	 * @author yang.lu
	 */
	public static String[] splitToArray(String str, char delimiter) {
		List<String> strings = splitWorker(str, delimiter, false);
		return null == strings ? null : strings.toArray(ArrayUtil.EMPTY_STRING_ARRAY);
	}

	/**
	 * 分割字符串工作者方法
	 * <pre>
	 *     splitWorker("a,,b,c,", ',', true)  = ["a", "", "b", "c", ""] 保留空部分
	 *     splitWorker("a,,b,c,", ',', false) = ["a", "b", "c"] 过滤空部分
	 * </pre>
	 *
	 * @param str            要分割的字符串，如果为null则返回null
	 * @param delimiter      分隔符字符
	 * @param keepEmptyParts 是否保留空部分
	 *                       true：保留所有分割部分，包括空字符串。["a", "", "b", "c", ""] (5个部分)
	 *                       false：过滤掉空字符串部分，只保留有内容的。["a", "b", "c"] (3个部分，空部分被过滤)
	 * @return 分割后的数组列表，如果输入为null则返回null，空字符串返回空列表
	 * @author yang.lu
	 */
	private static List<String> splitWorker(String str, char delimiter, boolean keepEmptyParts) {
		// 空值检查
		if (null == str) {
			return null;
		}

		final int length = str.length();
		if (length == 0) {
			return Collections.emptyList();
		}
		final List<String> list = new ArrayList<>();
		// 当前令牌的起始位置
		int start = 0;

		for (int i = 0; i < length; i++) {
			if (str.charAt(i) == delimiter) {
				// 只有当需要保留所有令牌，或者当前令牌非空时才添加
				if (keepEmptyParts || i > start) {
					list.add(str.substring(start, i));
				}
				// 下一个令牌的起始位置
				start = i + 1;
			}
		}

		// 处理最后一个令牌
		if (keepEmptyParts || length > start) {
			list.add(str.substring(start, length));
		}

		return list;
	}

	/**
	 * 比较两个字符序列的指定区域是否匹配（支持忽略大小写）
	 *
	 * @param cs         主字符序列（被比较的源序列）
	 * @param ignoreCase 是否忽略大小写比较
	 *                   true：不区分大小写比较
	 *                   false：区分大小写比较
	 * @param thisStart  主字符序列中比较区域的起始索引位置
	 * @param substring  要比较的子字符序列
	 * @param start      子字符序列中比较区域的起始索引位置
	 * @param length     要比较的字符数量
	 * @return 如果指定区域的字符匹配则返回true，否则返回false
	 * @author yang.lu
	 */
	static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {

		// 如果两个字符序列都是String类型，直接调用String类的regionMatches方法
		if (cs instanceof String && substring instanceof String) {
			return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
		}

		// 初始化比较索引和长度计数器
		int index1 = thisStart;    // 主字符序列的当前比较位置
		int index2 = start;        // 子字符序列的当前比较位置
		int tmpLen = length;    // 剩余需要比较的字符数

		// 提前获取长度以便检测NPE，保持与java.lang.String版本相同的异常行为
		final int srcLen = cs.length() - thisStart;        // 主字符序列从起始位置到末尾的剩余长度
		final int otherLen = substring.length() - start;    // 子字符序列从起始位置到末尾的剩余长度

		// 检查参数有效性：起始位置和长度不能为负数
		if (thisStart < 0 || start < 0 || length < 0) {
			return false;
		}

		// 检查区域长度是否足够：两个序列的剩余长度都必须不小于要比较的长度
		if (srcLen < length || otherLen < length) {
			return false;
		}

		// 逐个字符比较指定长度的区域
		while (tmpLen-- > 0) {
			// 从两个序列中分别获取当前要比较的字符
			final char c1 = cs.charAt(index1++);
			final char c2 = substring.charAt(index2++);

			// 如果字符完全相同，继续比较下一个字符
			if (c1 == c2) {
				continue;
			}

			// 如果要求区分大小写且字符不相等，直接返回不匹配
			if (!ignoreCase) {
				return false;
			}

			// 忽略大小写比较：使用与String#regionMatches相同的逻辑
			// 先转换为大写比较
			final char u1 = Character.toUpperCase(c1);
			final char u2 = Character.toUpperCase(c2);
			if (u1 != u2 && Character.toLowerCase(u1) != Character.toLowerCase(u2)) {
				return false;
			}
		}

		// 所有字符都比较完毕且匹配，返回true
		return true;
	}

	/**
	 * 替换指定字符串的指定区间内字符为 "*"
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
	 * 替换指定字符串的指定区间内字符为固定字符
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
			return null;
		}

		String originalStr = str.toString();
		int[] codePoints = originalStr.codePoints().toArray();
		int len = codePoints.length;

		/*
			替换范围有效性检查：
    		1. 开始位置超出字符串长度，无需替换
			2. 开始位置大于等于结束位置，参数逻辑错误
		 */
		if (startInclude >= len || startInclude >= endExclude) {
			return originalStr;
		}

		// 规范化结束位置：确保不超出字符串的码点长度边界
		endExclude = Math.min(endExclude, len);

		// 足够容量，避免扩容。原字符串长度 + 替换字符可能带来的长度变化 + 安全边界
		StringBuilder sb = new StringBuilder(originalStr.length() + (endExclude - startInclude) + 16);

		// 遍历所有码点位置，根据位置决定替换或保留原字符
		for (int i = 0; i < len; i++) {
			if (i >= startInclude && i < endExclude) {
				// 当前码点位于替换范围内：使用指定字符替换
				sb.append(replacedChar);
			} else {
				// 当前码点位于替换范围外：保留原字符
				int cp = codePoints[i];
				/*
					Unicode字符处理优化：
					- 基本多文种平面字符（BMP）：U+0000 ~ U+FFFF，可直接转换为char
				 	- 补充字符：U+10000 ~ U+10FFFF，需要代理对表示
				 */
				if (cp < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
					// BMP字符：直接转换为char类型追加，性能最优
					sb.append((char) cp);
				} else {
					// 补充字符：分解为高位代理和低位代理分别追加
					sb.append(Character.highSurrogate(cp));
					sb.append(Character.lowSurrogate(cp));
				}
			}
		}
		return sb.toString();
	}
}
