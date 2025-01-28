package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class CpfUniqueViolationException extends RuntimeException {
	
	private String resource;
	private String code;
	
	public CpfUniqueViolationException(String message) {
		super(message);
	}
	
	public CpfUniqueViolationException(String resource, String code) {
		this.resource = resource;
		this.code = code;
	}

}
