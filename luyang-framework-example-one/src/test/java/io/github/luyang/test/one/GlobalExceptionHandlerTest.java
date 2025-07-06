package io.github.luyang.test.one;

import cn.hutool.core.lang.Validator;
import io.github.luyang.starter.base.api.Result;
import io.github.luyang.starter.base.error.ExceptionAssert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yang.lu
 */
public class GlobalExceptionHandlerTest extends BaseMockMvcTest {

	@TestConfiguration
	static class TestControllerConfig {
		@RestController
		@RequestMapping("/api/test")
		static class ExceptionTestController {

			@GetMapping("/error")
			public Result<Void> throwError() {
				throw new IllegalArgumentException("参数不合法");
			}

			@GetMapping("/error1/{email}")
			public Result<Void> throwError(@PathVariable("email") String email) {
				TestEnum.EMAIL_INVALID.isFalse(!Validator.isEmail(email));
				return Result.success();
			}
		}
	}

	@Test
	void testRuntimeExceptionCaptured() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/test/error"))
			.andExpect(status().isInternalServerError())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		System.out.println(responseBody);
	}

	@Test
	void testExceptionAssert() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/test/error1/123163.com"))
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		System.out.println(responseBody);
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	enum TestEnum implements ExceptionAssert<String> {

		EMAIL_INVALID("EMAIL_INVALID", "无效邮箱格式");
		private final String code;
		private final String message;
	}
}
