package com.mballem.demoparkapi.web.controller;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mballem.demoparkapi.entity.ClientSpot;
import com.mballem.demoparkapi.repository.projection.ClientSpotProjection;
import com.mballem.demoparkapi.service.ClientSpotService;
import com.mballem.demoparkapi.service.ParkingService;
import com.mballem.demoparkapi.web.dto.PageableDto;
import com.mballem.demoparkapi.web.dto.ParkingCreateDto;
import com.mballem.demoparkapi.web.dto.ParkingResponseDto;
import com.mballem.demoparkapi.web.dto.mapper.ClientSpotMapper;
import com.mballem.demoparkapi.web.dto.mapper.PageableMapper;
import com.mballem.demoparkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Parkings", description = "Operations to register the entry and exit of a vehicle from the parking lot.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parkings")
public class ParkingController {
	
	private final ParkingService parkingService;
	private final ClientSpotService clientSpotService;
	
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
    
    @Operation(summary = "Find a Parked Vehicle", description = "Resource to find a Parked Vehicle " +
            "by receipt number. Request requires a Bearer Token.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                @Parameter(in = PATH, name = "receipt", description = "Receipt number generated by check-in")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ParkingResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Receipt number not found.",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/check-in/{receipt}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<ParkingResponseDto> getByReceipt(@PathVariable String receipt){
    	ClientSpot clientSpot = clientSpotService.findByReceipt(receipt);
    	ParkingResponseDto dto = ClientSpotMapper.toDto(clientSpot);
    	return ResponseEntity.ok(dto);
    }
    
    @Operation(summary = "Checkout Operation", description = "Resource for leaving a vehicle from the parking lot. " +
            "Request requires a Bearer Token. Restricted access to 'ADMIN' Role.",
            security = @SecurityRequirement(name = "security"),
            parameters = { @Parameter(in = PATH, name = "receipt", description = "Receipt number generated by check-in",
                    required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource updated successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ParkingResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Receipt number is missing or " +
                    		"the vehicle has already been checked out.",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resource not allowed for Client profile",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/check-out/{receipt}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkout(@PathVariable String receipt){
    	ClientSpot clientSpot = parkingService.checkOut(receipt);
    	ParkingResponseDto dto = ClientSpotMapper.toDto(clientSpot);
    	return ResponseEntity.ok(dto);
    }
    
    @Operation(summary = "Find Customer parking records by CPF", description = "Find " +
            "Customer parking records by CPF. Request requires a Bearer Token.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = PATH, name = "cpf", description = "CPF number for the client to be consulted",
                            required = true
                    ),
                    @Parameter(in = QUERY, name = "page", description = "Represents the returned page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))
                    ),
                    @Parameter(in = QUERY, name = "size", description = "Represents the total number of elements per page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5"))
                    ),
                    @Parameter(in = QUERY, name = "sort", description = "Default sort field 'entryDate,asc'. ",
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "entryDate,asc")),
                            hidden = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PageableDto.class))),
                    @ApiResponse(responseCode = "403", description = "Resource not allowed for Client profile",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/cpf/{cpf}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllParkingsByCpf(@PathVariable String cpf,
    													   @PageableDefault(size = 5, sort = "entryDate",
    													   direction = Sort.Direction.ASC) Pageable pageable) {
    	Page<ClientSpotProjection> projection = clientSpotService.findAllByClientCpf(cpf, pageable);
    	PageableDto dto = PageableMapper.toDto(projection);
    	return ResponseEntity.ok(dto);
    }

}
