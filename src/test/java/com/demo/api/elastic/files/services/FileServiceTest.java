package com.demo.api.elastic.files.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.mock.web.MockMultipartFile;

/**
 * @author Khawla Rouis
 * @version 1.0
 * @since Nov 01, 2025
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class FileServiceTest {

	@Mock
	private ElasticsearchOperations elasticsearchOperations;

	@InjectMocks
	private FileService fileService;

	@Test
	void testSaveTxtFile() {
		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Sample content".getBytes());
		fileService.saveFile(file);
		verify(elasticsearchOperations).index(any(), any());
	}

	@Test
	void testSaveJsonFile() {
		String jsonContent = "{\"key\": \"value\"}";
		MockMultipartFile file = new MockMultipartFile("file", "test.json", "application/json", jsonContent.getBytes());
		fileService.saveFile(file);
		verify(elasticsearchOperations).index(any(), any());
	}

	@Test
	void testSaveXmlFile() {
		String xmlContent = "<root><element>value</element></root>";
		MockMultipartFile file = new MockMultipartFile("file", "test.xml", "application/xml", xmlContent.getBytes());
		fileService.saveFile(file);
		verify(elasticsearchOperations).index(any(), any());
	}

	@Test
	void testSaveFileWithSpecificFileName() {
		String jsonContent = "{\"key\": \"value\"}";
		String fileName = "87-20250731188503686-example.json";
		MockMultipartFile file = new MockMultipartFile("file", fileName, "application/json", jsonContent.getBytes());
		fileService.saveFile(file);
		verify(elasticsearchOperations).index(any(), eq(IndexCoordinates.of("87")));
		assertEquals("87", fileService.extractIndex(fileName));
		assertEquals("JSON", fileService.detectFileType(fileName));
	}
}
