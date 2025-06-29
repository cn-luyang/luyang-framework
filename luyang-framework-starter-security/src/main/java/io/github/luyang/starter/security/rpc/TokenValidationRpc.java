package io.github.luyang.starter.security.rpc;

import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.UnifiedPrincipal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("platform-auth")
public interface TokenValidationRpc {

	@PostMapping("/auth/introspect")
	Result<UnifiedPrincipal> validateToken(@RequestParam("accessToken") String accessToken);
}
