package com.mballem.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.mballem.demoparkapi.entity.Client;
import com.mballem.demoparkapi.web.dto.ClientCreateDto;
import com.mballem.demoparkapi.web.dto.ClientResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {
	
	public static Client toClient(ClientCreateDto dto) {
		return new ModelMapper().map(dto, Client.class);
	}
	
	public static ClientResponseDto toDto(Client client) {
		return new ModelMapper().map(client, ClientResponseDto.class);
	}

}
