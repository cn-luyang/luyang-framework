package io.github.luyang.starter.security.support.context;

import io.github.luyang.starter.base.context.CurrentUserAccessor;

/**
 * 当前操作用户实现
 *
 * @author yang.lu
 */
public class SecurityCurrentUserAccessor implements CurrentUserAccessor {

	@Override
	public String getOperatorId() {
		/*AuthSubject subject = SecurityUtil.getSubject();
		if (subject == null) return null;
		return subject.principal().getId();
		return subject.principal().getId();*/
		return null;
	}

	@Override
	public String getOperatorName() {
		/*AuthSubject subject = SecurityUtil.getSubject();
		if (subject == null) return null;
		return subject.principal().getName();*/
		return null;
	}

	@Override
	public String getOit() {
		/*AuthSubject subject = SecurityUtil.getSubject();
		if (subject == null) {
			return null;
		}
		return subject.principalType().name();*/
		return null;
	}
}
