package com.demo.api.elastic.files.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.api.elastic.files.models.FileSaveResponse;
import com.demo.api.elastic.files.services.FileService;

/**
 * @author Khawla Rouis
 * @version 1.0
 * @since Nov 01, 2025
 **/

@RestController
@RequestMapping("/api/files")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileService;

	@PostMapping(value = "/upload",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<FileSaveResponse> uploadFile(@RequestParam(value = "file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		try {
			String indexName = fileService.saveFile(file);
			return ResponseEntity.ok(new FileSaveResponse("File uploaded successfully to Index: ", indexName));
		} catch (UncategorizedElasticsearchException e) {
			logger.error("Elasticsearch error occurred while uploading file", e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new FileSaveResponse("Authentication failed while connecting to Elasticsearch.", null));
		}

		catch (Exception e) {
			logger.error("An unexpected error occurred while uploading file", e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new FileSaveResponse("An unexpected error occurred. Please try again later.", null));
		}
	}
}
