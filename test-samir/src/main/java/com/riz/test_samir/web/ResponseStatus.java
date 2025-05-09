package com.riz.test_samir.web;

import lombok.Getter;

@Getter
public enum ResponseStatus {

	// SUCCESS
	GENERAL_SUCCESS(1000, "General Success"),
	DATA_CREATED(1002, "Data Created"),

	GENERAL_WARN(3000, "General Warning"),


	// FAIL,
	GENERAL_FAIL(4000, "General Failure"),
	GENERAL_CONFLICT(4001, "Data already exists"),
	BAD_REQUEST(4002, "Bad Request"),
	GENERAL_NOT_FOUND(4004, "Data Not Found"),

	// ERROR
	GENERAL_ERROR(5000, "General Error"),

	UNAUTHORIZED(4001, "Unauthorized");


	ResponseStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private final int code;
	private final String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}


}
