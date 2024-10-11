package com.mballem.demoparkapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.User;
import com.mballem.demoparkapi.exception.UsernameUniqueViolationException;
import com.mballem.demoparkapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

	@Transactional
	public User save(User user) {
		try {
			return userRepository.save(user);
		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(String.format("Username [%s] already registered", user.getUsername()));
		}
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("User not found.")
		);
		
	}

	@Transactional
	public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword)) {
			throw new RuntimeException("The new password do not match with password confirmation.");
		}
		
		User user = findById(id);
		if (!user.getPassword().equals(currentPassword)) {
			throw new RuntimeException("Your password do not match.");
		}
		
		user.setPassword(newPassword);
		return user;
	}

	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
