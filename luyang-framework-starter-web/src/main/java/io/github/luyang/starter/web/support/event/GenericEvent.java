package io.github.luyang.starter.web.support.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * 通用泛型事件包装器
 *
 * @author yang.lu
 */
@Getter
public class GenericEvent<T extends BusinessEvent> extends ApplicationEvent implements ResolvableTypeProvider {

	/**
	 * 事件载体数据
	 */
	private final T body;

	public GenericEvent(Object source, T body) {
		super(source);
		this.body = body;
	}

	/**
	 * 防止 Spring 泛型擦除导致 @EventListener
	 *
	 * @author yang.lu
	 */
	@Override
	public ResolvableType getResolvableType() {
		return ResolvableType.forClassWithGenerics(getClass(), body.getClass());
	}
}
