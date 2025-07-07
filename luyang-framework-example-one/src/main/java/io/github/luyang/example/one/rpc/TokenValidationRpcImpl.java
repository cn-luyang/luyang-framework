package io.github.luyang.example.one.rpc;

import cn.hutool.core.map.MapUtil;
import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.UnifiedPrincipal;
import io.github.luyang.starter.security.constant.enums.PrincipalType;
import io.github.luyang.starter.security.rpc.TokenValidationRpc;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author yang.lu
 */
@DubboService
@RequiredArgsConstructor
public class TokenValidationRpcImpl implements TokenValidationRpc {

	@Override
	public Result<UnifiedPrincipal> validateToken(String accessToken) {
		return Result.success(new UnifiedPrincipal("123", "123", PrincipalType.USER, MapUtil.newHashMap()));
	}
}
