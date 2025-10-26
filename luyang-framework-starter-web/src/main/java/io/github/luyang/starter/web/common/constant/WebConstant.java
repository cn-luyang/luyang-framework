package io.github.luyang.starter.web.common.constant;

public interface WebConstant {

    /** 请求头日志链路 ID，用于追踪请求唯一标识符 */
    String TRACE_ID_HEADER = "LY-Trace-Id";

    /** MDC日志链路 ID，用于追踪请求唯一标识符 */
    String TRACE_ID_MDC = "traceId";
}
