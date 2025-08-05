package com.litmus7.empManagement.constants;

public class StatusCodes {
	// Success codes
	public static final int SUCCESS = 200;
	public static final int PARTIAL_SUCCESS = 207;
	
	// Error codes
	public static final int FAILURE = 400;
	public static final int FILE_NOT_FOUND = 404;
	public static final int INVALID_FORMAT = 415;
	public static final int DATABASE_ERROR = 500;
	public static final int DUPLICATE_ENTRY = 409;
	public static final int VALIDATION_ERROR = 422;
	public static final int PARSING_ERROR = 400;
	public static final int CONNECTION_ERROR = 503;
}