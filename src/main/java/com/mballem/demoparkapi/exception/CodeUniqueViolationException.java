package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class CodeUniqueViolationException extends RuntimeException {
	
	private String code;

	public CodeUniqueViolationException(String code) {
		this.code = code;
	}

}
