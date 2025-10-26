package io.github.luyang.base.util.date;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 日期格式化类，提供常用的日期格式化对象
 *
 * @author yang.lu
 */
public class DatePattern {

    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日期时间格式化器：yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter NORM_DATETIME_FORMATTER = createFormatter(NORM_DATETIME_PATTERN);

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 标准日期格式化器：yyyy-MM-dd
     */
    public static final DateTimeFormatter NORM_DATE_FORMATTER = createFormatter(NORM_DATE_PATTERN);

    /**
     * 标准时间格式：HH:mm:ss
     */
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";

    /**
     * 标准时间格式化器：HH:mm:ss
     */
    public static final DateTimeFormatter NORM_TIME_FORMATTER = createFormatter(NORM_TIME_PATTERN);

    /**
     * 纯数字日期时间格式：yyyyMMddHHmmss
     */
    public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

    /**
     * 纯数字日期时间格式化器：yyyyMMddHHmmss
     */
    public static final DateTimeFormatter PURE_DATETIME_FORMATTER = createFormatter(PURE_DATETIME_PATTERN);

    /**
     * 纯数字日期格式：yyyyMMdd
     */
    public static final String PURE_DATE_PATTERN = "yyyyMMdd";

    /**
     * 纯数字日期格式化器：yyyyMMdd
     */
    public static final DateTimeFormatter PURE_DATE_FORMATTER = createFormatter(PURE_DATE_PATTERN);

    /**
     * 纯数字时间格式：HHmmss
     */
    public static final String PURE_TIME_PATTERN = "HHmmss";

    /**
     * 纯数字时间格式化器：HHmmss
     */
    public static final DateTimeFormatter PURE_TIME_FORMATTER = createFormatter(PURE_TIME_PATTERN);

    /**
     * 中文日期时间格式：yyyy年MM月dd日 HH时mm分ss秒
     */
    public static final String CHINESE_DATETIME_PATTERN = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 中文日期时间格式化器：yyyy年MM月dd日 HH时mm分ss秒
     */
    public static final DateTimeFormatter CHINESE_DATETIME_FORMATTER = createFormatter(CHINESE_DATETIME_PATTERN);

    /**
     * 中文日期格式：yyyy年MM月dd日
     */
    public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";

    /**
     * 中文日期格式化器：yyyy年MM月dd日
     */
    public static final DateTimeFormatter CHINESE_DATE_FORMATTER = createFormatter(CHINESE_DATE_PATTERN);

    /**
     * 创建日期时间格式化器
     *
     * @param pattern 日期格式模式
     * @return 日期时间格式化器
     * @author yang.lu
     */
    public static DateTimeFormatter createFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
    }
}
