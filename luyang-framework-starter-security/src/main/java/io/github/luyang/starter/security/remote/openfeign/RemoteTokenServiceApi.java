package io.github.luyang.starter.security.remote.openfeign;

import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.AuthUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 远程 Token 服务接口
 *
 * @author yang.lu
 */
@FeignClient(name = "auth-service", path = "/rpc-api/token")
public interface RemoteTokenServiceApi {

	@GetMapping("/validate")
	Result<AuthUser> validateToken(String accessToken);
}
