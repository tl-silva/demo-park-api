package com.mballem.demoparkapi.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mballem.demoparkapi.entity.User;
import com.mballem.demoparkapi.service.UserService;
import com.mballem.demoparkapi.web.dto.UserCreateDto;
import com.mballem.demoparkapi.web.dto.UserPasswordDto;
import com.mballem.demoparkapi.web.dto.UserResponseDto;
import com.mballem.demoparkapi.web.dto.mapper.UserMapper;
import com.mballem.demoparkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Users", description = "Contains all operations related to resources for registering, editing and reading a User.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	private final UserService userService;
	
	@Operation(summary = "Create a new User", description = "Resource to create a new User",
			responses = {
					@ApiResponse(responseCode = "201", description = "Resource created successfully",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
					@ApiResponse(responseCode = "409", description = "Username [email] already registered in the system",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			
			})
	@PostMapping
	public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto user){
		User newUser = userService.save(UserMapper.toUser(user));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(newUser));
	}
	
	@Operation(summary = "Retrieves a User by ID", description = "Retrieves a User using the ID specified",
			responses = {
					@ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
					@ApiResponse(responseCode = "404", description = "Resource not found",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			
			})
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
	public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
		User newUser = userService.findById(id);
		return ResponseEntity.ok(UserMapper.toDto(newUser));
	}
	
	@Operation(summary = "Update password", description = "Update registered User password",
			responses = {
					@ApiResponse(responseCode = "204", description = "Password updated successfully",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
					@ApiResponse(responseCode = "400", description = "Password does not match",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "Resource not found",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "422", description = "Invalid or bad formatted fields",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

					
			})
	@PatchMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') AND (#id == authentication.principal.id)")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto userPasswordDto){
		User newUser = userService.editPassword(id, userPasswordDto.getCurrentPassword(), userPasswordDto.getNewPassword(), userPasswordDto.getConfirmPassword());
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "List all users", description = "List all existing users",
			responses = {
					@ApiResponse(responseCode = "200", description = "List of all registered users",
							content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class))))
			})
	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getAll(){
		List<User> users = userService.findAll();
		return ResponseEntity.ok(UserMapper.toListDto(users));
	}
	
}
