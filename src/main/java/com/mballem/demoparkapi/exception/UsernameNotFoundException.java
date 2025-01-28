package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class UsernameNotFoundException  extends RuntimeException {
	
	private String username;
	
	public UsernameNotFoundException(String username) {
		this.username = username;
	}

}
