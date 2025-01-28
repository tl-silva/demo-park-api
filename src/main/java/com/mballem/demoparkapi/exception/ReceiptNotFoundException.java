package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class ReceiptNotFoundException extends RuntimeException {
	
	private String receipt;

	public ReceiptNotFoundException(String receipt) {
		this.receipt = receipt;
	}
	
}
