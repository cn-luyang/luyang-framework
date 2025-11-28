package io.github.luyang.base.util.text;

/**
 * 字符常量定义类，集中管理所有字符常量，避免魔法字符
 *
 * @author yang.lu
 */
public final class CharConstants {

    private CharConstants() {
    }

    // 空白字符
    static final char SPACE       = ' ';
    static final char TAB         = '\t';
    static final char CR          = '\r';
    static final char LF          = '\n';

    // 标点符号
    static final char DOT         = '.';
    static final char COMMA       = ',';
    static final char SEMICOLON   = ';';
    static final char COLON       = ':';
    static final char QUESTION    = '?';
    static final char EXCLAMATION = '!';

    // 分隔符
    static final char SLASH       = '/';
    static final char BACKSLASH   = '\\';
    static final char DASH        = '-';
    static final char UNDERLINE   = '_';
    static final char PIPE        = '|';

    // 运算符
    static final char AMPERSAND   = '&';
    static final char ASTERISK    = '*';
    static final char EQUALS      = '=';
    static final char PLUS        = '+';
    static final char PERCENT     = '%';

    // 特殊符号
    static final char AT          = '@';
    static final char POUND       = '#';
    static final char DOLLAR      = '$';
    static final char CARET       = '^';
    static final char TILDE       = '~';

    // 括号
    static final char LEFT_PAREN  = '(';
    static final char RIGHT_PAREN = ')';
    static final char LEFT_BRACE  = '{';
    static final char RIGHT_BRACE = '}';
    static final char LEFT_BRACKET= '[';
    static final char RIGHT_BRACKET= ']';

    // 引号
    static final char SINGLE_QUOTE= '\'';
    static final char DOUBLE_QUOTE= '"';
    static final char BACKTICK    = '`';

    // 比较符号
    static final char LESS_THAN   = '<';
    static final char GREATER_THAN= '>';
}
