package com.mballem.demoparkapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.Spot;
import com.mballem.demoparkapi.entity.Spot.SpotStatus;
import com.mballem.demoparkapi.exception.AvailableSpotException;
import com.mballem.demoparkapi.exception.CodeUniqueViolationException;
import com.mballem.demoparkapi.exception.SpotCodeNotFoundException;
import com.mballem.demoparkapi.repository.SpotRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SpotService {
	
	private final SpotRepository spotRepository;
	
	@Transactional
	public Spot save(Spot spot) {
		try {
			return spotRepository.save(spot);
		} catch (DataIntegrityViolationException ex) {
			throw new CodeUniqueViolationException(spot.getCode());
		}
	}
	
	@Transactional(readOnly = true)
	public Spot findByCode(String code) {
		return spotRepository.findByCode(code).orElseThrow(
				() -> new SpotCodeNotFoundException(code.toString()));
	}

	@Transactional(readOnly = true)
	public Spot findByFreeSpot() {
		return spotRepository.findFirstByStatus(SpotStatus.FREE).orElseThrow(
				() -> new AvailableSpotException());
	}

}
