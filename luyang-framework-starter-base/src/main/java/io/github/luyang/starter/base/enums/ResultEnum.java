package io.github.luyang.starter.base.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码
 *
 * @author yang.lu
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultEnum implements IBaseEnum<String> {

    SUCCESS("0", "Success"),
    FAILURE("500", "Failure");

    private final String code;
    private final String message;
}
