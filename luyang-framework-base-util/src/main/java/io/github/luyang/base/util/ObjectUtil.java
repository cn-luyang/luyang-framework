package io.github.luyang.base.util;

import java.util.Objects;

/**
 * 对象工具类
 *
 * @author yang.lu
 */
public class ObjectUtil {

    /**
     * 检查对象是否为null
     *
     * @param obj 待检查的对象
     * @return 如果对象为null，则返回true，否则返回false
     * @author yang.lu
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 检查对象是否不为null
     *
     * @param obj 待检查的对象
     * @return 如果对象不为null，则返回true，否则返回false
     * @author yang.lu
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 对象为null时返回默认值
     *
     * @param <T>          对象类型
     * @param object       待检查的对象
     * @param defaultValue 默认值
     * @return 如果对象为null，则返回默认值，否则返回对象本身
     * @author yang.lu
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return isNull(object) ? defaultValue : object;
    }

    public static boolean equals(Object obj1, Object obj2) {
        return equal(obj1, obj2);
    }

    public static boolean equal(Object obj1, Object obj2) {
        if (obj1 instanceof Number && obj2 instanceof Number) {
            return NumberUtil.equals((Number) obj1, (Number) obj2);
        }
        return Objects.equals(obj1, obj2);
    }
}
