package com.mballem.demoparkapi.web.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientCreateDto {
	
	@NotBlank(message = "{NotBlank.clientCreateDto.name}")
	@Size(min = 5, max = 100, message = "{Size.clientCreateDto.name}")
	private String name;
	
	@CPF(message = "{CPF.clientCreateDto.cpf}")
	@Size(min = 11, max = 11, message = "{Size.clientCreateDto.cpf}")
	private String cpf;

}
