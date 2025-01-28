package com.mballem.demoparkapi.web.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParkingCreateDto {
	
	@NotBlank(message = "{NotBlank.parkingCreateDto.licensePlate}")
	@Size(min = 8, max = 8, message = "{Size.parkingCreateDto.licensePlate}")
	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message ="{Pattern.parkingCreateDto.licensePlate}")
	private String licensePlate;
	
	@NotBlank(message = "{NotBlank.parkingCreateDto.brand}")
	private String brand;
	
	@NotBlank(message = "{NotBlank.parkingCreateDto.model}")
	private String model;
	
	@NotBlank(message = "{NotBlank.parkingCreateDto.color}")
	private String color;
	
	@NotBlank(message = "{NotBlank.parkingCreateDto.clientCpf}")
	@Size(min = 11, max = 11, message = "{Size.parkingCreateDto.clientCpf}")
	@CPF(message = "{CPF.parkingCreateDto.clientCpf}")
	private String clientCpf;

}
