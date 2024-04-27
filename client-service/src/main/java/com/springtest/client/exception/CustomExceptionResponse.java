package com.springtest.client.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomExceptionResponse {

	private Date timestamp;
	private String message;
	private String details;
	private String name;

}
