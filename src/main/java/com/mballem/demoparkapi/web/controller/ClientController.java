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
import com.mballem.demoparkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Clients", description = "Contains all operations related to a Client Resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {
	
	private final ClientService clientService;
	private final UserService userService;
	
	@Operation(summary = "Create a new Client", description = "Resource to create a new client linked to an already registered user",
			responses = {
					@ApiResponse(responseCode = "201", description = "Resource created successfully",
							content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ClientResponseDto.class))),
					@ApiResponse(responseCode = "409", description = "CLient [CPF] already registered in the system",
							content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
							content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "403", description = "Resource not allowed for Admin profile",
					content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
	
			})
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
