package com.mballem.demoparkapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.ClientSpot;
import com.mballem.demoparkapi.exception.EntityNotFoundException;
import com.mballem.demoparkapi.repository.ClientSpotRepository;
import com.mballem.demoparkapi.repository.projection.ClientSpotProjection;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientSpotService {
	
	private final ClientSpotRepository clientSpotRepository;
	
	@Transactional
	public ClientSpot save(ClientSpot clientSpot) {
		return clientSpotRepository.save(clientSpot);
	}

	@Transactional(readOnly = true)
	public ClientSpot findByReceipt(String receipt) {
		return clientSpotRepository.findByReceiptAndExitDateIsNull(receipt).orElseThrow(
				() -> new EntityNotFoundException(
						String.format("Receipt '%s' not found in the system or checkout already completed.", receipt)
				)
		);
	}

	@Transactional(readOnly = true)
	public long getTotalTimesParkingFull(String cpf) {
		return clientSpotRepository.countByClientCpfAndExitDateIsNotNull(cpf);
	}

	@Transactional(readOnly = true)
	public Page<ClientSpotProjection> findAllByClientCpf(String cpf, Pageable pageable) {
		return clientSpotRepository.findAllByClientCpf(cpf, pageable);
	}

	@Transactional(readOnly = true)
	public Page<ClientSpotProjection> findAllByUserId(Long id, Pageable pageable) {
		return clientSpotRepository.findAllByClientUserId(id, pageable);
	}

}