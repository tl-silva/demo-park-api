package com.mballem.demoparkapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.User;
import com.mballem.demoparkapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("User not found.")
		);
		
	}

	@Transactional
	public User editPassword(Long id, String password) {
		User user = findById(id);
		user.setPassword(password);
		return user;
	}

	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
