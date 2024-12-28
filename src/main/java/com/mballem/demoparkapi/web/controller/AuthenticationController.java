package com.mballem.demoparkapi.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mballem.demoparkapi.jwt.JwtToken;
import com.mballem.demoparkapi.jwt.JwtUserDetailsService;
import com.mballem.demoparkapi.web.dto.UserLoginDto;
import com.mballem.demoparkapi.web.dto.UserResponseDto;
import com.mballem.demoparkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Authentication", description = "Resource to proceed with API Authentication")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

	private final JwtUserDetailsService detailsService;
	private final AuthenticationManager authenticationManager;
	
	@Operation(summary = "Authenticate to API", description = "Authentication resource in API",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful Authentication and return of a Bearer Token",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
					@ApiResponse(responseCode = "400", description = "Invalid Credentials",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "422", description = "Invalid field(s)",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			
			})
	@PostMapping("/auth")
	public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto dto, HttpServletRequest request){
		log.info("Login authentication process {}", dto.getUsername());
		try {
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
			
			authenticationManager.authenticate(authenticationToken);
			
			JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
			
			return ResponseEntity.ok(token);
		} catch (AuthenticationException ex) {
			log.warn("Bad credentials from username '{}'", dto.getUsername());
		}
		return ResponseEntity
				.badRequest()
				.body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid Credentials"));
	}
}
