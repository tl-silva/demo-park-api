package com.mballem.demoparkapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.User;
import com.mballem.demoparkapi.exception.EntityNotFoundException;
import com.mballem.demoparkapi.exception.PasswordInvalidException;
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
				() -> new EntityNotFoundException(String.format("User id=%s not found. ", id))
		);
		
	}

	@Transactional
	public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword)) {
			throw new PasswordInvalidException("The new password does not match with password confirmation.");
		}
		
		User user = findById(id);
		if (!user.getPassword().equals(currentPassword)) {
			throw new PasswordInvalidException("Your password does not match.");
		}
		
		user.setPassword(newPassword);
		return user;
	}

	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public User finByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException(String.format("User with [%s] not found. ", username))		
		);
	}

	@Transactional(readOnly = true)
	public User.Role findRoleByUsername(String username) {
		return userRepository.findRoleByUsername(username);
	}

}
