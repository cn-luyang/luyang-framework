package io.github.luyang.base.util.text;

import java.nio.charset.StandardCharsets;

/**
 * 字符串常量池
 *
 * @author yang.lu
 */
public class StrCharConstants {

    /**
     * 将字符转换为字符串
     * 单字符字符串被JVM自动驻留，不会重复创建
     *
     * @param c 字符
     * @return 对应的字符串
     * @author yang.lu
     */
    static String str(char c) {
        return String.valueOf(c);
    }


    // ==================== 基础字符串 ====================

	// 基础字符串
	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String TAB = "\t";
	public static final String CR = "\r";
	public static final String LF = "\n";
	public static final String CRLF = "\r\n";

	// 标点符号字符串
	public static final String DOT = ".";
	public static final String COMMA = ",";
	public static final String SEMICOLON = ";";
	public static final String COLON = ":";
	public static final String QUESTION_MARK = "?";
	public static final String EXCLAMATION_MARK = "!";

	// 路径分隔符字符串
	public static final String SLASH = "/";
	public static final String BACKSLASH = "\\";
	public static final String DOUBLE_SLASH = "//";

	// 连接符字符串
	public static final String DASH = "-";
	public static final String UNDERLINE = "_";
	public static final String PIPE = "|";

	// 运算符字符串
	public static final String AMPERSAND = "&";
	public static final String ASTERISK = "*";
	public static final String EQUALS = "=";
	public static final String PLUS = "+";
	public static final String PERCENT = "%";

	// 特殊符号字符串
	public static final String AT = "@";
	public static final String POUND = "#";
	public static final String DOLLAR = "$";
	public static final String CARET = "^";
	public static final String TILDE = "~";

	// 括号字符串
	public static final String LEFT_PARENTHESIS = "(";
	public static final String RIGHT_PARENTHESIS = ")";
	public static final String LEFT_BRACE = "{";
	public static final String RIGHT_BRACE = "}";
	public static final String LEFT_BRACKET = "[";
	public static final String RIGHT_BRACKET = "]";

	// 引号字符串
	public static final String SINGLE_QUOTE = "'";
	public static final String DOUBLE_QUOTE = "\"";

	// JSON 相关字符串
	public static final String EMPTY_JSON = "{}";
	public static final String EMPTY_JSON_ARRAY = "[]";
	public static final String NULL = "null";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String JSON_START = "{";
	public static final String ARRAY_START = "[";

	// 编码相关字符串
	public static final String UTF_8 = StandardCharsets.UTF_8.name();
	public static final String ISO_8859_1 = StandardCharsets.ISO_8859_1.name();
	public static final String GBK = "GBK";

	// 协议相关字符串
	public static final String HTTP = "http";
	public static final String HTTPS = "https";

	// XML 相关字符串
	public static final String XML_START = "<?xml";
}
