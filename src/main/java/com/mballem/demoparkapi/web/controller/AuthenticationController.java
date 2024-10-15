package com.mballem.demoparkapi.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mballem.demoparkapi.jwt.JwtToken;
import com.mballem.demoparkapi.jwt.JwtUserDetailsService;
import com.mballem.demoparkapi.web.dto.UserLoginDto;
import com.mballem.demoparkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

	private final JwtUserDetailsService detailsService;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping("/auth")
	public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto dto, HttpServletRequest request){
		log.info("Login authentication process {}", dto.getUsername());
		try {
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
			
			authenticationManager.authenticate(authenticationToken);
			
			JwtToken token = detailsService.getTokenAtuthenticated(dto.getUsername());
			
			return ResponseEntity.ok(token);
		} catch (AuthenticationException ex) {
			log.warn("Bad credentials from username '{}'", dto.getUsername());
		}
		return ResponseEntity
				.badRequest()
				.body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid Credentials"));
	}
}
