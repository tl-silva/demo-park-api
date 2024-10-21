package com.mballem.demoparkapi.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mballem.demoparkapi.entity.Client;
import com.mballem.demoparkapi.jwt.JwtUserDetails;
import com.mballem.demoparkapi.service.ClientService;
import com.mballem.demoparkapi.service.UserService;
import com.mballem.demoparkapi.web.dto.ClientCreateDto;
import com.mballem.demoparkapi.web.dto.ClientResponseDto;
import com.mballem.demoparkapi.web.dto.mapper.ClientMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {
	
	private final ClientService clientService;
	private final UserService userService;
	
	@PostMapping
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<ClientResponseDto> create(@RequestBody @Valid ClientCreateDto dto,
													@AuthenticationPrincipal JwtUserDetails userDetails) {
		Client client = ClientMapper.toClient(dto);
		client.setUser(userService.findById(userDetails.getId()));
		clientService.save(client);
		return ResponseEntity.status(201).body(ClientMapper.toDto(client));
		
	}

}
