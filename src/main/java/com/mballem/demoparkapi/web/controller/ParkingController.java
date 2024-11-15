package com.mballem.demoparkapi.web.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mballem.demoparkapi.entity.ClientSpot;
import com.mballem.demoparkapi.service.ParkingService;
import com.mballem.demoparkapi.web.dto.ParkingCreateDto;
import com.mballem.demoparkapi.web.dto.ParkingResponseDto;
import com.mballem.demoparkapi.web.dto.mapper.ClientSpotMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parkings")
public class ParkingController {
	
	private final ParkingService parkingService;
	
	@PostMapping("/check-in")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ParkingResponseDto> checkin(@RequestBody @Valid ParkingCreateDto dto) {
		ClientSpot clientSpot = ClientSpotMapper.toClientSpot(dto);
		parkingService.checkIn(clientSpot);
		ParkingResponseDto responseDto = ClientSpotMapper.toDto(clientSpot);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequestUri()
				.path("/{receipt}")
				.buildAndExpand(clientSpot.getReceipt())
				.toUri();
		return ResponseEntity.created(location).body(responseDto);
	}
	

}
