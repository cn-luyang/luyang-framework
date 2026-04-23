package io.github.luyang.starter.mybatis.support.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.luyang.starter.base.constant.BaseConstant;
import io.github.luyang.starter.mybatis.common.model.BaseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自动填充字段
 *
 * @author yang.lu
 */
public class DefaultFieldHandler implements MetaObjectHandler {

	private final HttpServletRequest request;

	public DefaultFieldHandler(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void insertFill(MetaObject metaObject) {
		if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity entity) {
			LocalDateTime now = LocalDateTime.now();
			String userId = getUserId();

			setIfNull(entity.getCreatedTime(), () -> entity.setCreatedTime(now));
			setIfNull(entity.getUpdatedTime(), () -> entity.setUpdatedTime(now));
			setIfNull(entity.getCreatedBy(), () -> entity.setCreatedBy(userId));
			setIfNull(entity.getUpdatedBy(), () -> entity.setUpdatedBy(userId));
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity entity) {
			setIfNull(entity.getUpdatedTime(), () -> entity.setUpdatedTime(LocalDateTime.now()));
			setIfNull(entity.getUpdatedBy(), () -> entity.setUpdatedBy(getUserId()));
		}
	}

	private String getUserId() {
		return (String) request.getAttribute(BaseConstant.USER_ID);
	}

	private final <T> void setIfNull(T value, Runnable setter, Runnable... additionalSetters) {
		if (value == null) {
			setter.run();
			for (Runnable additional : additionalSetters) {
				additional.run();
			}
		}
	}
}

