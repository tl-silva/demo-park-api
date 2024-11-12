package com.mballem.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.mballem.demoparkapi.entity.Spot;
import com.mballem.demoparkapi.web.dto.SpotCreateDto;
import com.mballem.demoparkapi.web.dto.SpotResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotMapper {
	
	public static Spot toSpot(SpotCreateDto dto) {
		return new ModelMapper().map(dto, Spot.class);
	}
	
	public static SpotResponseDto toDto(Spot spot) {
		return new ModelMapper().map(spot, SpotResponseDto.class);
	}

}
