package io.github.luyang.starter.mybatis.support.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.luyang.starter.base.context.CurrentUserAccessor;
import io.github.luyang.starter.mybatis.common.model.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.ObjectProvider;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 自动填充字段
 *
 * @author yang.lu
 */
public record DefaultFieldHandler(
	ObjectProvider<CurrentUserAccessor> userAccessorProvider) implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity entity) {
			// 填充创建时间和更新时间
			LocalDateTime now = LocalDateTime.now();
			setIfNull(entity::getCreatedTime, entity::setCreatedTime, now);
			setIfNull(entity::getUpdatedTime, entity::setUpdatedTime, now);

			// 填充操作人和操作人身份类型
			setOperatorFields(entity, true);
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity entity) {
			// 填充更新时间
			setIfNull(entity::getUpdatedTime, entity::setUpdatedTime, LocalDateTime.now());
			// 填充和更新人身份类型
			setOperatorFields(entity, false);
		}
	}

	/**
	 * 填充操作人和操作人身份类型
	 *
	 * @param entity   基础实体
	 * @param isInsert 是否新增
	 * @author yang.lu
	 */
	private void setOperatorFields(BaseEntity entity, boolean isInsert) {
		Optional.ofNullable(userAccessorProvider.getIfAvailable())
			.ifPresent(accessor -> {
				if (isInsert) {
					setIfNull(entity::getCreatedBy, entity::setCreatedBy, accessor.getOperatorId());
					setIfNull(entity::getCreateOit, entity::setCreateOit, accessor.getOit());
				}

				setIfNull(entity::getUpdatedBy, entity::setUpdatedBy, accessor.getOperatorId());
				setIfNull(entity::getUpdatedOit, entity::setUpdatedOit, accessor.getOit());
			});
	}

	/**
	 * 填充字段值
	 *
	 * @param getter 原实体字段值
	 * @param setter 设置实体字段值
	 * @param value  待填充字段值
	 * @author yang.lu
	 */
	private <T> void setIfNull(Supplier<T> getter, Consumer<T> setter, T value) {
		if (null == getter.get() && null != value) {
			setter.accept(value);
		}
	}
}

