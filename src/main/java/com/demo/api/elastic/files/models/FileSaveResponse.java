package com.demo.api.elastic.files.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Khawla Rouis
 * @version 1.0
 * @since Nov 01, 2025
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileSaveResponse {

	private String message;

	private String index;
	

}
