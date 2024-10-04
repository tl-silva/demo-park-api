package com.mballem.demoparkapi.service;

import org.springframework.stereotype.Service;

import com.mballem.demoparkapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

}
