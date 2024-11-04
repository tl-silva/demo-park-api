package com.mballem.demoparkapi.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mballem.demoparkapi.entity.Client;
import com.mballem.demoparkapi.jwt.JwtUserDetails;
import com.mballem.demoparkapi.repository.projection.ClientProjection;
import com.mballem.demoparkapi.service.ClientService;
import com.mballem.demoparkapi.service.UserService;
import com.mballem.demoparkapi.web.dto.ClientCreateDto;
import com.mballem.demoparkapi.web.dto.ClientResponseDto;
import com.mballem.demoparkapi.web.dto.PageableDto;
import com.mballem.demoparkapi.web.dto.mapper.ClientMapper;
import com.mballem.demoparkapi.web.dto.mapper.PageableMapper;
import com.mballem.demoparkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Clients", description = "Contains all operations related to a Client Resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {
	
	private final ClientService clientService;
	private final UserService userService;
	
	@Operation(summary = "Create a new Client", description = "Resource to create a new client linked to an already registered user" +
	"Request requires a Bearer Token. Restricted access to 'CLIENT' Role.",
			security = @SecurityRequirement(name = "security"),
			responses = {
					@ApiResponse(responseCode = "201", description = "Resource created successfully",
							content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ClientResponseDto.class))),
					@ApiResponse(responseCode = "409", description = "Client [CPF] already registered in the system",
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
	
	@Operation(summary = "Find a Client", description = "Resource to find a client by id" +
			"Request requires a Bearer Token. Restricted access to 'ADMIN' Role.",
			security = @SecurityRequirement(name = "security"),
			responses = {
					@ApiResponse(responseCode = "201", description = "Resource located successfully",
							content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ClientResponseDto.class))),
					@ApiResponse(responseCode = "404", description = "Client not found",
							content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "403", description = "Resource not allowed for Client profile",
					content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
	
			})
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ClientResponseDto> getById(@PathVariable Long id){
		Client client = clientService.findById(id);
		return ResponseEntity.ok(ClientMapper.toDto(client));
	}
	
	@Operation(summary = "Retrieve a list of Clients",
			description = "Request requires a Bearer Token. Restricted access to 'ADMIN' Role.",
			security = @SecurityRequirement(name = "security"),
			parameters = {
					@Parameter(in = QUERY, name = "page",
							content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
							description = "Represents the page returned"
					),
					@Parameter(in = QUERY, name = "size",
							content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
							description = "Represents the total number of elements per page"
			        ),
					@Parameter(in = QUERY, name = "sort", hidden = true,
							array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,asc")),
							description = "Represents the sorting of results. Multiple acceptance criteria are supported.")		
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Resource located successfully",
							content = @Content(mediaType = "application/json;charset=UTF-8",
									schema = @Schema(implementation = ClientResponseDto.class))
					),
					@ApiResponse(responseCode = "403", description = "Resource not allowed for Client profile",
							content = @Content(mediaType = "application/json;charset=UTF-8",
									schema = @Schema(implementation = ErrorMessage.class))),
	
			})
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true) @PageableDefault(size = 5, sort = {"name"}) Pageable pageable){
		Page<ClientProjection> clients = clientService.findAll(pageable);
		return ResponseEntity.ok(PageableMapper.toDto(clients));
	}

}
