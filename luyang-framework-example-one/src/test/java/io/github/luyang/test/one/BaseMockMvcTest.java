package io.github.luyang.test.one;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @author yang.lu
 */
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseMockMvcTest {

	private static final Logger logger = LoggerFactory.getLogger(BaseMockMvcTest.class);

	@Autowired
	protected MockMvc mockMvc;

	protected void printResponse(MvcResult result) throws Exception {
		logger.info(result.getResponse().getContentAsString());
	}
}
