package io.github.luyang.starter.security.remote.feign;

import io.github.luyang.starter.base.common.model.Result;
import io.github.luyang.starter.security.remote.dto.AccessTokenResp;
import io.github.luyang.starter.security.remote.dto.CheckTokenResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "open-service", path = "/auth")
public interface RemoteAuthFeign {

	@PostMapping("/token")
	Result<AccessTokenResp> getAccessToken(@RequestParam("grant_type") String grantType,
										   @RequestParam("client_id") String clientId,
										   @RequestParam("client_secret") String clientSecret);

	@GetMapping("/check-token")
	Result<CheckTokenResp> checkToken(@RequestParam("token") String token);
}
