package com.ptc.computation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ComputationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test() throws Exception {
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
								"""));
		}
	}
