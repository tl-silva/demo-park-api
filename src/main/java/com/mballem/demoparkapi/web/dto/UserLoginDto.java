package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDto {
	
	@NotBlank(message = "{NotBlank.userLoginDto.username}")
	@Email(regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "{Email.userLoginDto.username}" )
	private String username;
	
	@NotBlank(message = "{NotBlank.userLoginDto.password}")
	@Size(min = 6, max = 6, message = "{Size.userLoginDto.password}")
	private String password;
	
	

}
