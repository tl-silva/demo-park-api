package com.mballem.demoparkapi.web.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
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
import com.mballem.demoparkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/spots")
public class SpotController {
	
	private final SpotService spotService;
	
	@Operation(summary = "Create a new spot", description = "Resource to create a new Spot" +
			"Request requires a Bearer Token. Restricted access to 'ADMIN' Role.",
			security = @SecurityRequirement(name = "security"),
			responses = {
					@ApiResponse(responseCode = "201", description = "Resource created successfully",
							headers = @Header(name = HttpHeaders.LOCATION, description = "Url of the created resource")),
					@ApiResponse(responseCode = "409", description = "Spot already registered",
							content = @Content(mediaType = "application/json;charset=UTF-8",
									schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "422", description = "Resource not processed due to missing data or invalid data",
							content = @Content(mediaType = "application/json;charset=UTF-8",
									schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "403", description = "Resource not allowed for Client profile",
					content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

			})
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
	
	@Operation(summary = "Find a spot", description = "Resource to return a spot by your code" +
			"Request requires a Bearer Token. Restricted access to 'ADMIN' Role.",
			security = @SecurityRequirement(name = "security"),
			responses = {
					@ApiResponse(responseCode = "200", description = "Resource located successfully",
							content = @Content(mediaType = "application/json;charset=UTF-8",
									schema = @Schema(implementation = SpotResponseDto.class))),
					@ApiResponse(responseCode = "404", description = "Spot not found",
							content = @Content(mediaType = "application/json;charset=UTF-8",
									schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "403", description = "Resource not allowed for Client profile",
					content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

			})
	@GetMapping("/{code}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SpotResponseDto> getByCode(@PathVariable String code) {
		Spot spot = spotService.findByCode(code);
		return ResponseEntity.ok(SpotMapper.toDto(spot));
	}

}
