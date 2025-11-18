package io.github.luyang.starter.security.support.context;

import io.github.luyang.starter.base.common.context.CurrentUserAccessor;
import io.github.luyang.starter.security.support.identity.AuthSubject;
import io.github.luyang.starter.security.util.SecurityUtil;

/**
 * 当前操作用户实现
 *
 * @author yang.lu
 */
public class SecurityCurrentUserAccessor implements CurrentUserAccessor {

	@Override
	public String getOperatorId() {
		AuthSubject subject = SecurityUtil.getSubject();
		if (subject == null) return null;
		return subject.principal().getId();
	}

	@Override
	public String getOperatorName() {
		AuthSubject subject = SecurityUtil.getSubject();
		if (subject == null) return null;
		return subject.principal().getName();
	}

	@Override
	public String getOit() {
		AuthSubject subject = SecurityUtil.getSubject();
		if (subject == null) {
			return null;
		}
		return subject.principalType().name();
	}
}
