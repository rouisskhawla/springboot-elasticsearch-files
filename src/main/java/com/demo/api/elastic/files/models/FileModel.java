package com.demo.api.elastic.files.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Khawla Rouis
 * @version 1.0
 * @since Nov 01, 2025
 **/

@Document(indexName = "default")
@Getter
@Setter
public class FileModel {

	@Id
	private String id;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
	@LastModifiedDate
	private Date date;

	@Field(type = FieldType.Keyword)
	private String fileName;

	@Field(type = FieldType.Keyword)
	private String fileContent;

	@Field(type = FieldType.Keyword)
	private String fileType;

	public FileModel() {
		super();
	}

}
