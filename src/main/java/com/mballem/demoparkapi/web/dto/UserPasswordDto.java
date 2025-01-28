package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserPasswordDto {
	
	@NotBlank(message = "{NotBlank.userPasswordDto.currentPassword}")
	@Size(min = 6, max = 6, message = "{Size.userPasswordDto.currentPassword}")
	private String currentPassword;
	
	@NotBlank(message = "{NotBlank.userPasswordDto.newPassword}")
	@Size(min = 6, max = 6, message = "{Size.userPasswordDto.newPassword}")
	private String newPassword;
	
	@NotBlank(message = "{NotBlank.userPasswordDto.confirmPassword}")
	@Size(min = 6, max = 6, message = "{Size.userPasswordDto.confirmPassword}")
	private String confirmPassword;

}
