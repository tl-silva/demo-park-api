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
	
	@NotBlank
	@Size(min = 5, max = 100)
	private String name;
	
	@CPF
	@Size(min = 11, max = 11)
	private String cpf;

}
