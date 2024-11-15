package com.mballem.demoparkapi.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mballem.demoparkapi.entity.Spot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParkingResponseDto {
	
	private String licensePlate;
	private String brand;
	private String model;
	private String color;
	private String clientCpf;
	private String receipt;
	private LocalDateTime entryDate;
	private LocalDateTime exitDate;
	private Spot spotCode;
	private BigDecimal fee;
	private BigDecimal discount;

}
