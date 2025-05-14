package io.github.luyang.test.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.luyang.starter.dict.model.DictItem;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yang.lu
 */
public class DictTest extends BaseMockMvcTest {

	@Test
	void testCreateItem() throws Exception {
		DictItem dictItem = new DictItem();
		dictItem.setDictCode("sys_user_sex");
		dictItem.setItemName("男");
		dictItem.setItemValue("M");
		dictItem.setSortOrder(1);
		dictItem.setRemark("Remark for male");

		mockMvc.perform(post("/dict/item")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dictItem)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("0"))
			.andExpect(jsonPath("$.message").value("Success"));
	}

	@Test
	void testGetItems() throws Exception {
		MvcResult result = mockMvc.perform(get("/dict/sys_user_sex/items"))
			.andExpect(status().isOk())
			.andReturn();

		printResponse(result);

	}
}
