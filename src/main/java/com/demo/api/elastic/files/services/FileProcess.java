package com.demo.api.elastic.files.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * Methods in common for file processing
 * 
 * @author Khawla Rouis
 * @version 1.0
 * @since Nov 01, 2025
 **/

public class FileProcess {

	protected String detectFileType(String fileName) {
		return switch (fileName.substring(fileName.lastIndexOf(".") + 1)) {
		case "xml" -> "XML";
		case "json" -> "JSON";
		case "txt" -> "TXT";
		default -> "UNKNOWN";
		};
	}

	protected String extractContent(MultipartFile file) {
		try {
			return new String(file.getBytes());
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	protected String extractIndex(String fileName) {
		if (fileName.contains("-")) {
			String[] parts = fileName.split("-");
			if (parts.length > 0) {
				return parts[0];
			}
		}
		return "default";
	}

}
