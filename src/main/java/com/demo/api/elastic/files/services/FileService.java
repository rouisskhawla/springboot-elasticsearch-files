package com.demo.api.elastic.files.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.api.elastic.files.models.FileModel;

/**
 * @author Khawla Rouis
 * @version 1.0
 * @since Nov 01, 2025
 **/

@Service
public class FileService extends FileProcess {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	public String saveFile(MultipartFile file) {
		// Extract file information
		try {

			String fileName = file.getOriginalFilename();
			String fileType = detectFileType(fileName);
			String fileContent = extractContent(file);

			// Create FileDocument instance
			FileModel fileModel = new FileModel();
			fileModel.setFileName(fileName);
			fileModel.setFileType(fileType);
			fileModel.setFileContent(fileContent);
			fileModel.setDate(new Date());

			// Save to Elasticsearch with custom index
			return saveFileToIndex(fileModel);
		} catch (Exception e) {
			logger.error("Error saving file", e);
			throw e;
		}

	}

	public String saveFileToIndex(FileModel fileModel) {
		String indexName = extractIndex(fileModel.getFileName());
		IndexQuery indexQuery = new IndexQueryBuilder().withObject(fileModel).withIndex(indexName).build();
		elasticsearchOperations.index(indexQuery, IndexCoordinates.of(indexName));
		return indexName;

	}

}
