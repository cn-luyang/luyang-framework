package io.github.luyang.starter.security.remote;

import io.github.luyang.starter.base.common.model.Result;
import io.github.luyang.starter.security.AuthUser;
import org.springframework.web.bind.annotation.RequestParam;

public interface SecurityTokenRpc {

    Result<AuthUser> checkToken(@RequestParam("token") String token);
}
