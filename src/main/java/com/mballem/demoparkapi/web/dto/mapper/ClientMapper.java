package com.mballem.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.mballem.demoparkapi.web.dto.ClientCreateDto;
import com.mballem.demoparkapi.web.dto.ClientResponseDto;

import ch.qos.logback.core.net.server.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {
	
	private static Client toClient(ClientCreateDto dto) {
		return new ModelMapper().map(dto, Client.class);
	}
	
	private static ClientResponseDto toDto(Client client) {
		return new ModelMapper().map(client, ClientResponseDto.class);
	}

}
