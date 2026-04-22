package io.github.luyang.base.util;

/**
 * 字符工具类
 *
 * @author yang.lu
 */
public final class CharUtil {

	private CharUtil() {
	}

	/**
	 * 检查字符序列是否被指定字符包围
	 *
	 * @param str        待检查字符序列
	 * @param prefixChar 前缀字符
	 * @param suffixChar 后缀字符
	 * @return 是否被包围
	 * @author yang.lu
	 */
	public static boolean isWrap(CharSequence str, char prefixChar, char suffixChar) {
		if (str == null || str.length() < 2) {
			return false;
		}
		return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
	}

	/**
	 * 检查是否为不可见或空白字符（包括特殊 Unicode 字符）
	 *
	 * @param c 字符码点
	 * @return 是否为空白字符
	 * @author yang.lu
	 */
	public static boolean isBlankChar(int c) {
		if (Character.isWhitespace(c) || Character.isSpaceChar(c)) {
			return true;
		}

		return switch (c) {
			case '\ufeff', // BOM
				 '\u202a', // LRE
				 '\u0000', // Null
				 '\u3164', // Hangul Filler
				 '\u2800', // Braille Blank
				 '\u200c', // ZWNJ
				 '\u180e'  // MVS
				-> true;
			default -> false;
		};
	}
}
