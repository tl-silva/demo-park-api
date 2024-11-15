package com.mballem.demoparkapi.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.Client;
import com.mballem.demoparkapi.entity.ClientSpot;
import com.mballem.demoparkapi.entity.Spot;
import com.mballem.demoparkapi.util.ParkingUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ParkingService {
	
	private final ClientSpotService clientSpotService;
	private final ClientService clientService;
	private final SpotService spotService;
	
	@Transactional
	public ClientSpot checkIn(ClientSpot clientSpot) {
		Client client = clientService.findByCpf(clientSpot.getClient().getCpf());
		clientSpot.setClient(client);
		
		Spot spot = spotService.findByFreeSpot();
		spot.setStatus(Spot.SpotStatus.OCCUPIED);
		clientSpot.setSpot(spot);
		
		clientSpot.setEntryDate(LocalDateTime.now());
		
		clientSpot.setReceipt(ParkingUtils.issueReceipt());
		
		return clientSpotService.save(clientSpot);
	}
}
