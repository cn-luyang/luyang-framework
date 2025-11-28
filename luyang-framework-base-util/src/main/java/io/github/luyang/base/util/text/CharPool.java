package io.github.luyang.base.util.text;

/**
 * 字符常量定义类，集中管理所有字符常量，避免魔法字符
 *
 * @author yang.lu
 */
public interface CharPool {

	// 空白字符
	char SPACE 				= ' ';
	char TAB 				= '\t';
	char CR 				= '\r';
	char LF 				= '\n';

	// 标点符号
	char DOT 				= '.';
	char COMMA 				= ',';
	char SEMICOLON 			= ';';
	char COLON 				= ':';
	char QUESTION 			= '?';
	char EXCLAMATION 		= '!';

	// 分隔符
	char SLASH 				= '/';
	char BACKSLASH 			= '\\';
	char DASH 				= '-';
	char UNDERLINE 			= '_';
	char PIPE 				= '|';

	// 运算符
	char AMPERSAND 			= '&';
	char ASTERISK 			= '*';
	char EQUALS 			= '=';
	char PLUS 				= '+';
	char PERCENT 			= '%';

	// 特殊符号
	char AT 				= '@';
	char POUND 				= '#';
	char DOLLAR 			= '$';
	char CARET 				= '^';
	char TILDE 				= '~';

	// 括号
	char LEFT_PAREN 		= '(';
	char RIGHT_PAREN 		= ')';
	char LEFT_BRACE 		= '{';
	char RIGHT_BRACE 		= '}';
	char LEFT_BRACKET 		= '[';
	char RIGHT_BRACKET 		= ']';

	// 引号
	char SINGLE_QUOTE 		= '\'';
	char DOUBLE_QUOTE 		= '"';
	char BACKTICK 			= '`';

	// 比较符号
	char LESS_THAN 			= '<';
	char GREATER_THAN 		= '>';
}
