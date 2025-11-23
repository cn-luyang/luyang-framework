package io.github.luyang.base.util.text;

import io.github.luyang.base.util.ArrayUtil;
import io.github.luyang.base.util.StrUtil;

/**
 * 字符串格式化工具类
 *
 * @author yang.lu
 */
public final class StrFormatter {

    /**
     * 私有构造方法，防止工具类被实例化
     *
     * @author yang.lu
     */
    private StrFormatter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 使用默认占位符 {} 格式化字符串
     *
     * @param strPattern 字符串模板，使用 {} 作为占位符
     * @param argArray   参数列表
     * @return 格式化后的字符串
     * @author yang.lu
     */
    public static String format(String strPattern, Object... argArray) {
        return formatWith(strPattern, StrUtil.EMPTY_JSON, argArray);
    }

    /**
     * 使用指定占位符格式化字符串
     * <pre>{@code
     * String text = StrFormatter.formatWith("File path: \\{}", "{}", "/home/user");
     *     // 输出：File path: {}
     * }
     * </pre>
     *
     * @param strPattern  字符串模板
     * @param placeHolder 占位符，如 "{}"、"{{}}"
     * @param argArray    参数列表
     * @return 格式化后的字符串
     */
    public static String formatWith(final String strPattern, final String placeHolder, final Object... argArray) {
        if (StrUtil.isBlank(strPattern) || StrUtil.isBlank(placeHolder) || ArrayUtil.isEmpty(argArray)) {
            return strPattern;
        }

        final int patternLen = strPattern.length();
        final int holderLen = placeHolder.length();
        // 提前预分配容量提升性能
        final var sbuf = new StringBuilder(patternLen + 50);

        int handledPos = 0;
        for (var argIndex = 0; argIndex < argArray.length; argIndex++) {
            final int delimIndex = strPattern.indexOf(placeHolder, handledPos);
            if (delimIndex == -1) {
                // 无占位符剩余：若从头开始即无占位符，直接返回原模板
                if (handledPos == 0) return strPattern;
                sbuf.append(strPattern, handledPos, patternLen);
                return sbuf.toString();
            }

            // 判断占位符前是否为转义符 '\'
            if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == CharPool.BACKSLASH) {
                // 若前面还有一个反斜杠，则认为转义符自身被转义，占位符仍有效
                if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == CharPool.BACKSLASH) {
                    sbuf.append(strPattern, handledPos, delimIndex - 1);
                    sbuf.append(""/*StrUtil.utf8Str(argArray[argIndex])*/);
                    handledPos = delimIndex + holderLen;
                } else {
                    // 否则占位符被转义，仅输出占位符字符本身
                    argIndex--;
                    sbuf.append(strPattern, handledPos, delimIndex - 1)
                        .append(placeHolder.charAt(0));
                    handledPos = delimIndex + 1;
                }
            } else {
                // 正常占位符替换
                sbuf.append(strPattern, handledPos, delimIndex)
                    .append(/*StrUtil.utf8Str(argArray[argIndex])*/"");
                handledPos = delimIndex + holderLen;
            }
        }

        // 添加末尾未处理部分
        sbuf.append(strPattern, handledPos, patternLen);
        return sbuf.toString();
    }
}
