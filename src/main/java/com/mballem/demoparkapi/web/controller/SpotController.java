package com.mballem.demoparkapi.web.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mballem.demoparkapi.entity.Spot;
import com.mballem.demoparkapi.service.SpotService;
import com.mballem.demoparkapi.web.dto.SpotCreateDto;
import com.mballem.demoparkapi.web.dto.SpotResponseDto;
import com.mballem.demoparkapi.web.dto.mapper.SpotMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/spots")
public class SpotController {
	
	private final SpotService spotService;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> create(@RequestBody @Valid SpotCreateDto dto) {
		Spot spot = SpotMapper.toSpot(dto);
		spotService.save(spot);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequestUri()
				.path("/{code}")
				.buildAndExpand(spot.getCode())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/{code}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SpotResponseDto> getByCode(@PathVariable String code) {
		Spot spot = spotService.findByCode(code);
		return ResponseEntity.ok(SpotMapper.toDto(spot));
	}

}
