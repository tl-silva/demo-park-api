package com.mballem.demoparkapi.exception;

public class CodeUniqueViolationException extends RuntimeException {
	
	public CodeUniqueViolationException(String message) {
		super(message);
	}

}
