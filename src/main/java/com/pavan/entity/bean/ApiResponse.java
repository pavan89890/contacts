package com.pavan.entity.bean;

import org.springframework.http.HttpStatus;

public class ApiResponse {
	private HttpStatus httpStatus;
	private String message;
	private Object data;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ApiResponse(HttpStatus httpStatus, String message, Object data) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.data = data;
	}

}
