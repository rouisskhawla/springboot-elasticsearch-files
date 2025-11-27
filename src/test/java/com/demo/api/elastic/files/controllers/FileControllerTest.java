package com.demo.api.elastic.files.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.demo.api.elastic.files.services.FileService;

/**
 * @author Khawla Rouis
 * @version 1.0
 * @since Nov 01, 2025
 **/

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

	@Mock
	private FileService fileService;

	@InjectMocks
	private FileController fileController;

	private MockMvc mockMvc;

	@Test
	void testUploadTxtFile() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Sample content".getBytes());
		mockMvc.perform(multipart("/api/files/upload").file(file)).andExpect(status().isOk());

	}

	@Test
	void testUploadJsonFile() throws Exception {
		String jsonContent = "{\"key\": \"value\"}";
		MockMultipartFile file = new MockMultipartFile("file", "test.json", "application/json", jsonContent.getBytes());
		mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
		mockMvc.perform(multipart("/api/files/upload").file(file)).andExpect(status().isOk());
	}

	@Test
	void testUploadXmlFile() throws Exception {
		String xmlContent = "<root><element>value</element></root>";
		MockMultipartFile file = new MockMultipartFile("file", "test.xml", "application/xml", xmlContent.getBytes());
		mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
		mockMvc.perform(multipart("/api/files/upload").file(file)).andExpect(status().isOk());
	}

}
