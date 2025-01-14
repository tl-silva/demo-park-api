package com.mballem.demoparkapi.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ClientSpotProjection {
	
	String getLicensePlate();
	String getBrand();
	String getModel();
	String getColor();
	String getClientCpf();
	String getReceipt();
	String getSpotCode();
	BigDecimal getFee();
	BigDecimal getDiscount();
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	LocalDateTime getEntryDate();
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	LocalDateTime getExitDate();
	

}
