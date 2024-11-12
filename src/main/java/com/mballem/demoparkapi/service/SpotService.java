package com.mballem.demoparkapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.Spot;
import com.mballem.demoparkapi.exception.CodeUniqueViolationException;
import com.mballem.demoparkapi.exception.EntityNotFoundException;
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
			throw new CodeUniqueViolationException(
					String.format("Spot with code '%s' already registered", spot.getCode())
			);
		}
	}
	
	@Transactional(readOnly = true)
	public Spot findByCode(String code) {
		return spotRepository.findByCode(code).orElseThrow(
				() -> new EntityNotFoundException(String.format("Spot with code '%s' was not found", code))
		);
	}

}
