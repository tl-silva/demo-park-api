package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserCreateDto {
	
	@NotBlank(message = "{NotBlank.userCreateDto.username}")
	@Email(regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "{Email.userCreateDto.username}")
	private String username;
	
	@NotBlank(message = "{NotBlank.userCreateDto.password}")
	@Size(min = 6, max = 6, message= "{Size.userCreateDto.password}")
	private String password;

}
