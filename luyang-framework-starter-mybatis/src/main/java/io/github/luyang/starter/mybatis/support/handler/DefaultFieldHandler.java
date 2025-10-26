package io.github.luyang.starter.mybatis.support.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.luyang.starter.base.common.constant.BaseConstant;
import io.github.luyang.starter.mybatis.common.model.BaseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 自动填充字段
 *
 * @author yang.lu
 */
public class DefaultFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject == null) return;

        if (Objects.requireNonNull(metaObject.getOriginalObject()) instanceof BaseEntity baseEntity) {
            var now = LocalDateTime.now();
            baseEntity.setCreatedTime(Objects.requireNonNullElse(baseEntity.getCreatedTime(), now));
            baseEntity.setUpdatedTime(Objects.requireNonNullElse(baseEntity.getUpdatedTime(), now));
            String userId = getUserId();
            baseEntity.setCreatedBy(userId);
            baseEntity.setUpdatedBy(userId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject == null) return;

        if (metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
            baseEntity.setUpdatedTime(
                Objects.requireNonNullElseGet(baseEntity.getUpdatedTime(), LocalDateTime::now)
            );

            String userId = getUserId();
            baseEntity.setUpdatedBy(userId);
        }
    }

    private String getUserId() {
        RequestAttributes att = RequestContextHolder.getRequestAttributes();
        if (!(att instanceof ServletRequestAttributes)) {
            // 非 Web 调用（定时器、MQ、单元测试等）
            return null;
        }

        HttpServletRequest req = ((ServletRequestAttributes) att).getRequest();
        return (String) req.getAttribute(BaseConstant.ATTR_USER_ID);
    }
}

