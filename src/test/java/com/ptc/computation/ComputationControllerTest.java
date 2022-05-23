package com.ptc.computation;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ComputationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test() throws Exception {
		MockMultipartFile file
				= new MockMultipartFile(
				"file",
				"input-file.csv",
				"text/csv",
				"""
				OPERATOR;NUMBER1;NUMBER2;NUMBER3
				+;2;4;
				+;5;5;
				*;10;2;2
				[2:]+[1:]=[:1];;;
				[2:]*[1:]=[:1];;;
				/;4;2;
				[1:1]*[1:2]=[:2];;;
				[5:]=[:3];;;
				[1:1]=[:1],[3:1]=[:2],[6:1]=[:3];;;""".getBytes()
		);

		mockMvc.perform(multipart("/api/computation").file(file))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/csv"))
				.andExpect(content().string("""
								RULE;RESULT1;RESULT2;RESULT3
								ADD;6;0;0
								ADD;10;0;0
								MULTI;40;0;0
								CUSTOM;16;0;0
								CUSTOM;60;0;0
								DIV;2;0;0
								CUSTOM;0;8;0
								CUSTOM;0;0;60
								CUSTOM;2;10;4
								TOTAL;136;18;64
								TOTAL;136;82;0
								TOTAL;218;0;0
								"""));
	}

	@Test
	void testGetAllRules() throws Exception {
		mockMvc.perform(
						get("/api/rules")
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].name").value("SUB"))
				.andExpect(jsonPath("$[0].description").value("Substracts numbers"));
	}

	@Test
	void testComputeAsync() throws Exception {
		MockMultipartFile file
				= new MockMultipartFile(
				"file",
				"input-file.csv",
				"text/csv",
				"""
				OPERATOR;NUMBER1;NUMBER2;NUMBER3
				+;2;4;
				+;5;5;
				*;10;2;2
				[2:]+[1:]=[:1];;;
				[2:]*[1:]=[:1];;;
				/;4;2;
				[1:1]*[1:2]=[:2];;;
				[5:]=[:3];;;
				[1:1]=[:1],[3:1]=[:2],[6:1]=[:3];;;""".getBytes()
		);

		MvcResult result = mockMvc.perform(multipart("/api/async/computation").file(file))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.uuid", matchesPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")))
				.andReturn();

		String uuid = JsonPath.read(result.getResponse().getContentAsString(), "$.uuid");

		mockMvc.perform(
						get("/api/async/computation/" + uuid)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/csv"))
				.andExpect(content().string("""
								RULE;RESULT1;RESULT2;RESULT3
								ADD;6;0;0
								ADD;10;0;0
								MULTI;40;0;0
								CUSTOM;16;0;0
								CUSTOM;60;0;0
								DIV;2;0;0
								CUSTOM;0;8;0
								CUSTOM;0;0;60
								CUSTOM;2;10;4
								TOTAL;136;18;64
								TOTAL;136;82;0
								TOTAL;218;0;0
								"""));
	}
}
