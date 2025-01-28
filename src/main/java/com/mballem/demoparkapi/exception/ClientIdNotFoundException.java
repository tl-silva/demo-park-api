package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class ClientIdNotFoundException extends RuntimeException {
	
	private String id;
	
	public ClientIdNotFoundException(String id) {
		this.id = id;
	}

}
