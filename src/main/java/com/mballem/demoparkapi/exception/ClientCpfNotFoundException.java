package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class ClientCpfNotFoundException extends RuntimeException {
	
	private String cpf;
	
	public ClientCpfNotFoundException(String cpf) {
		this.cpf = cpf;
	}

}
