package io.github.luyang.base.util.text;

/**
 * 字符常量池
 *
 * @author yang.lu
 */
public interface CharPool {

    // ==================== 空白字符 ====================

    /** 空格符 {@code ' '} */
    char SPACE          = Constants.SPACE;
    /** 制表符 {@code '\t'} */
    char TAB            = Constants.TAB;
    /** 回车符 {@code '\r'} */
    char CR             = Constants.CR;
    /** 换行符 {@code '\n'} */
    char LF             = Constants.LF;

    // ==================== 标点符号 ====================

    /** 点号 {@code '.'} */
    char DOT            = Constants.DOT;
    /** 逗号 {@code ','} */
    char COMMA          = Constants.COMMA;
    /** 分号 {@code ';'} */
    char SEMICOLON      = Constants.SEMICOLON;
    /** 冒号 {@code ':'} */
    char COLON          = Constants.COLON;
    /** 问号 {@code '?'} */
    char QUESTION_MARK  = Constants.QUESTION;
    /** 感叹号 {@code '!'} */
    char EXCLAMATION_MARK = Constants.EXCLAMATION;

    // ==================== 分隔符 ====================

    /** 斜杠 {@code '/'} */
    char SLASH          = Constants.SLASH;
    /** 反斜杠 {@code '\\'} */
    char BACKSLASH      = Constants.BACKSLASH;
    /** 连字符/减号 {@code '-'} */
    char DASH           = Constants.DASH;
    /** 下划线 {@code '_'} */
    char UNDERLINE      = Constants.UNDERLINE;
    /** 竖线 {@code '|'} */
    char PIPE           = Constants.PIPE;

    // ==================== 运算符 ====================

    /** 与符号 {@code '&'} */
    char AMPERSAND      = Constants.AMPERSAND;
    /** 星号/乘号 {@code '*'} */
    char ASTERISK       = Constants.ASTERISK;
    /** 等号 {@code '='} */
    char EQUALS         = Constants.EQUALS;
    /** 加号 {@code '+'} */
    char PLUS           = Constants.PLUS;
    /** 百分号 {@code '%'} */
    char PERCENT        = Constants.PERCENT;

    // ==================== 特殊符号 ====================

    /** at符号 {@code '@'} */
    char AT             = Constants.AT;
    /** 井号 {@code '#'} */
    char POUND          = Constants.POUND;
    /** 美元符号 {@code '$'} */
    char DOLLAR         = Constants.DOLLAR;
    /** 脱字符 {@code '^'} */
    char CARET          = Constants.CARET;
    /** 波浪号 {@code '~'} */
    char TILDE          = Constants.TILDE;

    // ==================== 括号 ====================

    /** 左圆括号 {@code '('} */
    char LEFT_PARENTHESIS   = Constants.LEFT_PAREN;
    /** 右圆括号 {@code ')'} */
    char RIGHT_PARENTHESIS  = Constants.RIGHT_PAREN;
    /** 左花括号 {@code '{'} */
    char LEFT_BRACE         = Constants.LEFT_BRACE;
    /** 右花括号 {@code '}'} */
    char RIGHT_BRACE        = Constants.RIGHT_BRACE;
    /** 左方括号 {@code '['} */
    char LEFT_BRACKET       = Constants.LEFT_BRACKET;
    /** 右方括号 {@code ']'} */
    char RIGHT_BRACKET      = Constants.RIGHT_BRACKET;

    // ==================== 引号 ====================

    /** 单引号 {@code '\''} */
    char SINGLE_QUOTE   = Constants.SINGLE_QUOTE;
    /** 双引号 {@code '"'} */
    char DOUBLE_QUOTE   = Constants.DOUBLE_QUOTE;
    /** 反引号 {@code '`'} */
    char BACKTICK       = Constants.BACKTICK;

    // ==================== 比较符号 ====================

    /** 小于号 {@code '<'} */
    char LESS_THAN      = Constants.LESS_THAN;
    /** 大于号 {@code '>'} */
    char GREATER_THAN   = Constants.GREATER_THAN;
}
