package com.luyang.test.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luyang.framework.starter.dict.model.DictItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yang.lu
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DictTest {

	@Autowired
	private MockMvc mockMvc;

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
		String responseBody = result.getResponse().getContentAsString();
		System.out.println(responseBody);
	}
}
