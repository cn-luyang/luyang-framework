package io.github.luyang.base.util;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author yang.lu
 */
public class NumberUtil {

    public static boolean equals(final Number number1, final Number number2) {
        if (number1 instanceof BigDecimal && number2 instanceof BigDecimal) {
            // BigDecimal使用compareTo方式判断，因为使用equals方法也判断小数位数，如2.0和2.00就不相等
            return equals((BigDecimal) number1, (BigDecimal) number2);
        }
        return Objects.equals(number1, number2);
    }
}
