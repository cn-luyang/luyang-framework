package io.github.luyang.base.util.text;

import java.nio.charset.StandardCharsets;

/**
 * 字符串常量池
 *
 * @author yang.lu
 */
public interface StrPool {

	// 基础字符串
	String EMPTY 			= "";
	String SPACE 			= " ";
	String TAB 				= "\t";
	String CARRIAGE_RETURN 	= "\r";
	String LINE_FEED 		= "\n";
	String CRLF 			= "\r\n";

	// 标点符号字符串
	String DOT 				= ".";
	String COMMA 			= ",";
	String SEMICOLON 		= ";";
	String COLON 			= ":";
	String QUESTION_MARK 	= "?";
	String EXCLAMATION_MARK = "!";

	// 路径分隔符字符串
	String SLASH 			= "/";
	String BACKSLASH 		= "\\";
	String DOUBLE_SLASH 	= "//";

	// 连接符字符串
	String DASH 			= "-";
	String UNDERLINE 		= "_";
	String PIPE 			= "|";

	// 运算符字符串
	String AMPERSAND 		= "&";
	String ASTERISK 		= "*";
	String EQUALS 			= "=";
	String PLUS 			= "+";
	String PERCENT 			= "%";

	// 特殊符号字符串
	String AT 				= "@";
	String POUND 			= "#";
	String DOLLAR 			= "$";
	String CARET 			= "^";
	String TILDE 			= "~";

	// 括号字符串
	String LEFT_PAREN 		= "(";
	String RIGHT_PAREN 		= ")";
	String LEFT_BRACE 		= "{";
	String RIGHT_BRACE 		= "}";
	String LEFT_BRACKET 	= "[";
	String RIGHT_BRACKET 	= "]";

	// 引号字符串
	String SINGLE_QUOTE 	= "'";
	String DOUBLE_QUOTE 	= "\"";

	// JSON 相关字符串
	String EMPTY_JSON 		= "{}";
	String EMPTY_JSON_ARRAY = "[]";
	String NULL 			= "null";
	String TRUE 			= "true";
	String FALSE 			= "false";
	String JSON_START 		= "{";
	String ARRAY_START 		= "[";

	// 编码相关字符串
	String UTF_8 			= StandardCharsets.UTF_8.name();
	String ISO_8859_1 		= StandardCharsets.ISO_8859_1.name();
	String GBK 				= "GBK";

	// 协议相关字符串
	String HTTP 			= "http";
	String HTTPS 			= "https";

	// XML 相关字符串
	String XML_START 		= "<?xml";
}
