package io.github.luyang.starter.web.features.desensitize;

import io.github.luyang.base.util.StrUtil;

/**
 * 脱敏规则
 *
 * @author yang.lu
 */
public enum DesensitizeRule {


    CUSTOMIZE {
        @Override
        String mask(String value) {
            return value;
        }
    },

    /**
     * 中文姓名
     */
    CHINESE_NAME {
        @Override
        String mask(String value) {

            return StrUtil.hide(value, 1, value.length());
        }
    },

    /**
     * 国内身份证号
     */
    CHINESE_ID_CARD {
        @Override
        String mask(String value) {
            return /*DesensitizedUtil.idCardNum(value, 6, 2);*/null;
        }
    },

    /**
     * 国内座机号
     */
    CHINESE_FIXED_PHONE {
        @Override
        String mask(String value) {
            return /*DesensitizedUtil.fixedPhone(value);*/null;
        }
    },

    /**
     * 国内手机号
     */
    CHINESE_MOBILE_PHONE {
        @Override
        String mask(String value) {
            return /*DesensitizedUtil.mobilePhone(value);*/null;
        }
    },

    /**
     * 国内地址
     */
    CHINESE_ADDRESS {
        @Override
        String mask(String value) {
            return /*DesensitizedUtil.address(value, 8);*/null;
        }
    },

    /**
     * 国内车牌
     */
    CHINESE_CAR_LICENSE {
        @Override
        String mask(String value) {
            return /*DesensitizedUtil.carLicense(value);*/null;
        }
    },

    /**
     * 国内银行卡
     */
    CHINESE_BANK_CARD {
        @Override
        String mask(String value) {
            return /*DesensitizedUtil.bankCard(value);*/null;
        }
    },

    /**
     * 电子邮件
     */
    EMAIL {
        @Override
        String mask(String value) {
            return /*DesensitizedUtil.email(value);*/null;
        }
    },

    /**
     * 密码
     */
    PASSWORD {
        @Override
        String mask(String value) {
            return /*DesensitizedUtil.password(value);*/null;
        }
    },

    /**
     * 出生日期
     */
    BIRTH_DATE {
        @Override
        String mask(String value) {
            StringBuilder builder = new StringBuilder();
            int digitCount = 0;
            for (char c : value.toCharArray()) {
                if (Character.isDigit(c)) {
                    digitCount++;
                    if (digitCount <= 2) {
                        builder.append(c);
                    } else {
                        builder.append('*');
                    }
                } else {
                    builder.append(c);
                }
            }
            return builder.toString();
        }
    };

    abstract String mask(String value);
}
