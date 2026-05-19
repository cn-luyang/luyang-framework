package io.github.luyang.starter.web.support.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 业务事件发布组件
 *
 * @author yang.lu
 */
@Component
public class BusinessEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public BusinessEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	/**
	 * 发布通用业务事件（自动包装成 GenericEvent）
	 *
	 * @param body 实现了 BusinessEvent 的Bean对象
	 * @param <T>  业务事件类型
	 * @author yang.lu
	 */
	public <T extends BusinessEvent> void publish(T body) {
		if (null == body) {
			return;
		}
		GenericEvent<T> genericEvent = new GenericEvent<>(this, body);
		applicationEventPublisher.publishEvent(genericEvent);
	}

	/**
	 * 发布 Spring 原生事件
	 *
	 * @author yang.lu
	 */
	public void publishRaw(Object event) {
		if (null == event) {
			return;
		}
		applicationEventPublisher.publishEvent(event);
	}
}
