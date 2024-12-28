package com.mballem.demoparkapi.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParkingResponseDto {
	
	private String licensePlate;
	private String brand;
	private String model;
	private String color;
	private String clientCpf;
	private String receipt;
	private String spotCode;
	private BigDecimal fee;
	private BigDecimal discount;
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime entryDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime exitDate;
	


}
