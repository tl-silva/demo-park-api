package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class UserIdNotFoundException extends RuntimeException {
	
	private String id;
	
	public UserIdNotFoundException(String id) {
		this.id = id;
	}

}
