package com.mballem.demoparkapi.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.mballem.demoparkapi.web.dto.UserResponseDto;
import com.mballem.demoparkapi.web.dto.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto user){
		User newUser = userService.save(UserMapper.toUser(user));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(newUser));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
		User newUser = userService.findById(id);
		return ResponseEntity.ok(UserMapper.toDto(newUser));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user){
		User newUser = userService.editPassword(id, user.getPassword());
		return ResponseEntity.ok(newUser);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		List<User> users = userService.findAll();
		return ResponseEntity.ok(users);
	}
	
}
