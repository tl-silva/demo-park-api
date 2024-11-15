package com.mballem.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.mballem.demoparkapi.entity.ClientSpot;
import com.mballem.demoparkapi.web.dto.ParkingCreateDto;
import com.mballem.demoparkapi.web.dto.ParkingResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientSpotMapper {

	public static ClientSpot toClientSpot(ParkingCreateDto dto) {
		return new ModelMapper().map(dto, ClientSpot.class);
	}
	
	public static ParkingResponseDto toDto(ClientSpot clientSpot) {
		return new ModelMapper().map(clientSpot, ParkingResponseDto.class);
	}

}
