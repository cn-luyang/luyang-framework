package io.github.luyang.starter.security.rpc;

import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.UnifiedPrincipal;

public interface TokenValidationRpc {

	Result<UnifiedPrincipal> validateToken(String accessToken);
}
