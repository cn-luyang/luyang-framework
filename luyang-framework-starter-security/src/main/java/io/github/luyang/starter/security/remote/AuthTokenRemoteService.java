package io.github.luyang.starter.security.remote;

import io.github.luyang.starter.base.model.Result;
import io.github.luyang.starter.security.remote.dto.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 认证令牌远程服务
 *
 * @author yang.lu
 */
@FeignClient(name = "/platform-uaa")
public interface AuthTokenRemoteService {

	/**
	 * 校验访问令牌
	 *
	 * @param token 令牌
	 * @return Result<TokenValidationResponse> 令牌校验结果及上下文信息
	 * @author yang.lu
	 */
	@GetMapping("/auth/check")
	Result<TokenValidationResponse> validateToken(String token);
}
