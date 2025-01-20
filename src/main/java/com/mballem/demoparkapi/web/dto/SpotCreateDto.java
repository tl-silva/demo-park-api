package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SpotCreateDto {
	
	@NotBlank(message = "{NotBlank.spotCreateDto.code}")
	@Size(min = 4, max = 4, message= "{Size.spotCreateDto.code}")
	private String code;
	
	@NotBlank(message = "{NotBlank.spotCreateDto.status}")
	@Pattern(regexp = "FREE|OCCUPIED", message = "{Pattern.spotCreateDto.status}")
	private String status;

}
