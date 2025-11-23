package io.github.luyang.base.util.exception;

/**
 * 工具类异常
 *
 * @author yang.lu
 */
public class UtilException extends RuntimeException {

    /**
     * 基于其他异常构造工具异常
     *
     * @param cause 原始异常
     * @author yang.lu
     */
    public UtilException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * 基于异常消息构造工具异常
     *
     * @param message 异常消息
     * @author yang.lu
     */
    public UtilException(String message) {
        super(message);
    }

    /**
     * 将检查异常转换为工具异常，并添加自定义消息
     *
     * @param throwable       原始异常
     * @param messageTemplate 消息模板
     * @param params          模板参数
     * @author yang.lu
     */
    public UtilException(Throwable throwable, String messageTemplate, Object... params) {
        //super(StrUtil.format(messageTemplate, params), throwable);
    }
}
