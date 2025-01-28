package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class SpotCodeNotFoundException extends RuntimeException {
	
	private String spotCode;
	
	public SpotCodeNotFoundException(String spotCode ) {
		this.spotCode = spotCode;
	}

}
