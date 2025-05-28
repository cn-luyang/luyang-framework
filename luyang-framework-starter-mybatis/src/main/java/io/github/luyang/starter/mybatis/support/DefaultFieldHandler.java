package io.github.luyang.starter.mybatis.support;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.luyang.starter.mybatis.beans.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自动填充字段
 *
 * @author yang.lu
 */
public class DefaultFieldHandler implements MetaObjectHandler {

	private static final String FIELD_CREATED_BY = "createdBy";
	private static final String FIELD_CREATED_TIME = "createdTime";

	private static final String FIELD_UPDATED_BY = "updatedBy";
	private static final String FIELD_UPDATED_TIME = "updatedTime";

	@Override
	public void insertFill(MetaObject metaObject) {
		if (null == metaObject || !(metaObject.getOriginalObject() instanceof BaseEntity)) {
			return;
		}

		LocalDateTime now = LocalDateTime.now();
		this.strictInsertFill(metaObject, FIELD_CREATED_TIME, LocalDateTime.class, now);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		var updateTime = getFieldValByName(FIELD_UPDATED_TIME, metaObject);
		if (null == updateTime) {
			this.strictUpdateFill(metaObject, FIELD_UPDATED_TIME, LocalDateTime.class, LocalDateTime.now());
		}
	}
}

