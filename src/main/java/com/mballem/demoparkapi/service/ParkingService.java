package com.mballem.demoparkapi.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ParkingService {
	
	private final ClientSpotService clientSpotService;
	private final ClientService clientService;
	private final SpotService spotService;

}
