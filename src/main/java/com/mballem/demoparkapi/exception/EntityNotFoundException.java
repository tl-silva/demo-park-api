package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

	private String resource;
	private String code;
	
	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(String resource, String code) {
		this.resource = resource;
		this.code = code;
	}
	
}
