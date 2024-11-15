package com.mballem.demoparkapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.ClientSpot;
import com.mballem.demoparkapi.repository.ClientSpotRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientSpotService {
	
	private final ClientSpotRepository clientSpotRepository;
	
	@Transactional
	public ClientSpot save(ClientSpot clientSpot) {
		return clientSpotRepository.save(clientSpot);
	}

}


