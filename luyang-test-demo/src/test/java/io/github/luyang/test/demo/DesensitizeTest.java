package io.github.luyang.test.demo;

import io.github.luyang.framework.starter.base.api.Result;
import io.github.luyang.framework.starter.web.desensitize.Desensitize;
import io.github.luyang.framework.starter.web.desensitize.DesensitizeRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yang.lu
 */
public class DesensitizeTest extends BaseMockMvcTest{

	private static final Logger logger = LoggerFactory.getLogger(DesensitizeTest.class);

	@TestConfiguration
	static class TestControllerConfig {
		@RestController
		@RequestMapping("/api/user")
		static
		class TestUserController {

			@GetMapping("/one")
			public Result<User> getOne() {
				User user = new User();
				user.setUsername("张三");
				user.setPassword("123456");
				user.setEmail("zhangsan@example.com");
				user.setPhone("13812345678");
				return Result.success(user);
			}

			@GetMapping("/list")
			public List<User> getList() {
				User u1 = new User("李四", "abcdef", "lisi@example.com", "13998765432");
				User u2 = new User("王五", "qwerty", "wangwu@example.com", "13711112222");
				return List.of(u1, u2);
			}
		}
	}

	@Test
	void testUserResponseIsDesensitized() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/user/one"))
			.andExpect(status().isOk())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		logger.info(responseBody);
	}

	@Test
	void testUserListDesensitized() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/user/list"))
			.andExpect(status().isOk())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		logger.info(responseBody);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class User {

		@Desensitize(rule = DesensitizeRule.CHINESE_NAME)
		private String username;

		@Desensitize(rule = DesensitizeRule.PASSWORD)
		private String password;

		@Desensitize(rule = DesensitizeRule.EMAIL)
		private String email;

		@Desensitize(rule = DesensitizeRule.CHINESE_MOBILE_PHONE)
		private String phone;
	}
}
