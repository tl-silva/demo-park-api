package com.mballem.demoparkapi.web.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
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
import com.mballem.demoparkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Parkings", description = "Operations to register the entry and exit of a vehicle from the parking lot.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parkings")
public class ParkingController {
	
	private final ParkingService parkingService;
	
    @Operation(summary = "Check-in Operation", description = "Resource for entering a vehicle into the parking lot. " +
    		"Request requires a Bearer Token. Restricted access to 'ADMIN' Role.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "Url of the created resource"),
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ParkingResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Possible causes: <br/>" +
                            "- Client's CPF not registered in the system; <br/>" +
                            "- No free parking spaces were found",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to missing data or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resource not allowed for Client profile",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
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
