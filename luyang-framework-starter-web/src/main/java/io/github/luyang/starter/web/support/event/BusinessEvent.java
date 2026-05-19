package io.github.luyang.starter.web.support.event;

import java.time.LocalDateTime;

/**
 * 业务事件标记接口
 *
 * @author yang.lu
 */
public interface BusinessEvent {

	/**
	 * 事件发生时间
	 *
	 * @author yang.lu
	 */
	default LocalDateTime getOccurredAt() {
		return LocalDateTime.now();
	}
}
