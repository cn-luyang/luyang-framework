package io.github.luyang.starter.security.remote;

import io.github.luyang.starter.base.common.model.Result;
import io.github.luyang.starter.security.AuthUser;

public interface SecurityTokenRpc {

	Result<AuthUser> checkToken(String token);
}
