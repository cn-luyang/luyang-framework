package io.github.luyang.base.util.text;

/**
 * 字符串常量池
 *
 * @author yang.lu
 */
public interface StrPool {

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

    /** 空字符串 {@code ""} */
    String EMPTY           = "";
    /** 空格字符串 {@code " "} */
    String SPACE           = str(Constants.SPACE);
    /** 制表符字符串 {@code "\t"} */
    String TAB             = str(Constants.TAB);
    /**  回车符字符串 {@code "\r"}  */
    String CR              = str(Constants.CR);
    /** 换行符字符串 {@code "\n"} */
    String LF              = str(Constants.LF);
    /**= 回车换行字符串 {@code "\r\n"} */
    String CRLF            = "\r\n";

    // ==================== 标点符号字符串 ====================

    /** 点号字符串 {@code "."} */
    String DOT             = str(Constants.DOT);
    /** 逗号字符串 {@code ","} */
    String COMMA           = str(Constants.COMMA);
    /** 分号字符串 {@code ";"} */
    String SEMICOLON       = str(Constants.SEMICOLON);
    /** 冒号字符串 {@code ":"} */
    String COLON           = str(Constants.COLON);
    /** 问号字符串 {@code "?"} */
    String QUESTION_MARK   = str(Constants.QUESTION);
    /** 感叹号字符串 {@code "!"} */
    String EXCLAMATION_MARK = str(Constants.EXCLAMATION);

    // ==================== 路径分隔符字符串 ====================

    /** 斜杠字符串 {@code "/"} */
    String SLASH           = str(Constants.SLASH);
    /** 反斜杠字符串 {@code "\\"} */
    String BACKSLASH       = str(Constants.BACKSLASH);
    /** 双斜杠字符串 {@code "//"} */
    String DOUBLE_SLASH    = "//";

    // ==================== 连接符字符串 ====================

    /** 连字符字符串 {@code "-"} */
    String DASH            = str(Constants.DASH);
    /** 下划线字符串 {@code "_"} */
    String UNDERLINE       = str(Constants.UNDERLINE);
    /** 竖线字符串 {@code "|"} */
    String PIPE            = str(Constants.PIPE);

    // ==================== 运算符字符串 ====================

    /** 与符号字符串 {@code "&"} */
    String AMPERSAND       = str(Constants.AMPERSAND);
    /** 星号字符串 {@code "*"} */
    String ASTERISK        = str(Constants.ASTERISK);
    /** 等号字符串 {@code "="} */
    String EQUALS          = str(Constants.EQUALS);
    /** 加号字符串 {@code "+"} */
    String PLUS            = str(Constants.PLUS);
    /** 百分号字符串 {@code "%"} */
    String PERCENT         = str(Constants.PERCENT);

    // ==================== 特殊符号字符串 ====================

    /** at符号字符串 {@code "@"} */
    String AT              = str(Constants.AT);
    /** 井号字符串 {@code "#"} */
    String POUND           = str(Constants.POUND);
    /** 美元符号字符串 {@code "$"} */
    String DOLLAR          = str(Constants.DOLLAR);

    // ==================== 括号字符串 ====================

    /** 左圆括号字符串 {@code "("} */
    String LEFT_PARENTHESIS   = str(Constants.LEFT_PAREN);
    /** 右圆括号字符串 {@code ")"} */
    String RIGHT_PARENTHESIS  = str(Constants.RIGHT_PAREN);
    /** 左花括号字符串 {@code "{"} */
    String LEFT_BRACE         = str(Constants.LEFT_BRACE);
    /** 右花括号字符串 {@code "}"} */
    String RIGHT_BRACE        = str(Constants.RIGHT_BRACE);
    /** 左方括号字符串 {@code "["} */
    String LEFT_BRACKET       = str(Constants.LEFT_BRACKET);
    /** 右方括号字符串 {@code "]"} */
    String RIGHT_BRACKET      = str(Constants.RIGHT_BRACKET);

    // ==================== 引号字符串 ====================

    /** 单引号字符串 {@code "'"} */
    String SINGLE_QUOTE   = str(Constants.SINGLE_QUOTE);
    /** 双引号字符串 {@code "\""} */
    String DOUBLE_QUOTE   = str(Constants.DOUBLE_QUOTE);

    // ==================== JSON相关字符串 ====================

    /** 空JSON对象字符串 {@code "{}"} */
    String EMPTY_JSON      = "{}";
    /** 空JSON数组字符串 {@code "[]"} */
    String EMPTY_JSON_ARRAY= "[]";
    /** null值字符串 {@code "null"} */
    String NULL            = "null";
    /** true值字符串 {@code "true"} */
    String TRUE            = "true";
    /** false值字符串 {@code "false"} */
    String FALSE           = "false";
    /** JSON对象开始字符串 {@code "{"} */
    String JSON_START      = "{";
    /** JSON数组开始字符串 {@code "["} */
    String ARRAY_START     = "[";

    // ==================== 编码相关字符串 ====================

    /** UTF-8编码字符串 {@code "UTF-8"} */
    String UTF_8           = "UTF-8";
    /** ISO-8859-1编码字符串 {@code "ISO-8859-1"} */
    String ISO_8859_1      = "ISO-8859-1";
    /** GBK编码字符串 {@code "GBK"} */
    String GBK             = "GBK";

    // ==================== 协议相关字符串 ====================

    /** HTTP协议字符串 {@code "http"} */
    String HTTP            = "http";
    /** HTTPS协议字符串 {@code "https"} */
    String HTTPS           = "https";

    // ==================== 格式标记字符串 ====================

    /**  XML开始标记字符串 {@code "<?xml"}  */
    String XML_START       = "<?xml";
}
