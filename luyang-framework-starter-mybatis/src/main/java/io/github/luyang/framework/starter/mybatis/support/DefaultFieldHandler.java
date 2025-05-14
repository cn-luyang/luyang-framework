package io.github.luyang.framework.starter.mybatis.support;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.luyang.framework.starter.mybatis.beans.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自动填充字段
 *
 * @author yang.lu
 */
public class DefaultFieldHandler implements MetaObjectHandler {

	private static final String FIELD_created_time = "createTime";
	private static final String FIELD_updated_time = "updateTime";
	private static final String FIELD_DELETED = "deleted";
	private static final String FIELD_DELETED_TIME = "deleteTime";

	@Override
	public void insertFill(MetaObject metaObject) {
		if (null == metaObject || !(metaObject.getOriginalObject() instanceof BaseEntity)) {
			return;
		}

		LocalDateTime now = LocalDateTime.now();
		this.strictInsertFill(metaObject, FIELD_created_time, LocalDateTime.class, now);
		this.strictInsertFill(metaObject, FIELD_updated_time, LocalDateTime.class, now);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		var updateTime = getFieldValByName(FIELD_updated_time, metaObject);
		if (null == updateTime) {
			this.strictUpdateFill(metaObject, FIELD_updated_time, LocalDateTime.class, LocalDateTime.now());
		}

		Boolean isDeleted = (Boolean) this.getFieldValByName(FIELD_DELETED, metaObject);
		if (isDeleted) {
			this.strictInsertFill(metaObject, FIELD_DELETED_TIME, LocalDateTime.class, LocalDateTime.now());
		}
	}
}

