package io.github.luyang.starter.base.model;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.luyang.starter.base.enums.IBaseEnum;
import io.github.luyang.starter.base.enums.ResultEnum;
import io.github.luyang.starter.base.util.TraceIdUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * 响应包装体
 *
 * @author yang.lu
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"requestId", "success", "code", "message", "data"})
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 2491076974985477952L;

	private String requestId;
    private String code;
    private String message;
    private boolean success;
    private T data;

    private Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.success = ResultEnum.SUCCESS.equals(code);
		this.requestId = TraceIdUtil.get();
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> failure(String message) {
        return failure(ResultEnum.FAILURE.getCode(), message);
    }

    public static <T> Result<T> failure(String code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> failure(String code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> failure(IBaseEnum<String> baseEnum) {
        return new Result<>(baseEnum.getCode(), baseEnum.getMessage(), null);
    }

    public static <T> Result<T> failure(IBaseEnum<String> baseEnum, String message) {
        return new Result<>(baseEnum.getCode(), message, null);
    }

    @Override
    public String toString() {
        final StringJoiner joiner = new StringJoiner(", ", "{", "}");

		Optional.ofNullable(requestId)
			.filter(StrUtil::isNotEmpty)
			.ifPresent(id -> joiner.add("\"requestId\":\"" + id + "\""));

        Optional.ofNullable(code)
            .filter(StrUtil::isNotEmpty)
            .ifPresent(c -> joiner.add("\"code\":" + c));

        Optional.ofNullable(message)
            .filter(StrUtil::isNotEmpty)
            .ifPresent(m -> joiner.add("\"message\":\"" + m + "\""));

        joiner.add("\"success\":" + success);

        Optional.ofNullable(data)
            .filter(ObjectUtil::isNotNull)
            .ifPresent(d -> joiner.add("\"data\":" + d));

        return joiner.toString();
    }
}
