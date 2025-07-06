package io.github.luyang.example.one.controller;

import cn.hutool.core.map.MapUtil;
import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.security.UnifiedPrincipal;
import io.github.luyang.starter.security.annotation.AnonymousAccess;
import io.github.luyang.starter.security.constant.enums.PrincipalType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yang.lu
 */
@RestController
public class TestOne {

	@GetMapping("/one")
	public void test() {
		System.out.println("x  One  X");
	}

	@AnonymousAccess
	@PostMapping("/introspect")
	public Result<UnifiedPrincipal> validateToken(@RequestParam("accessToken") String accessToken) {
		return Result.success(new UnifiedPrincipal("123", "123", PrincipalType.USER, MapUtil.newHashMap()));
	}
}
