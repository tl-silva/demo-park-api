package com.mballem.demoparkapi.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.User;
import com.mballem.demoparkapi.exception.InvalidPasswordException;
import com.mballem.demoparkapi.exception.PasswordDoesNotMatchException;
import com.mballem.demoparkapi.exception.UserIdNotFoundException;
import com.mballem.demoparkapi.exception.UsernameNotFoundException;
import com.mballem.demoparkapi.exception.UsernameUniqueViolationException;
import com.mballem.demoparkapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User save(User user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(user.getUsername());
		}
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new UserIdNotFoundException(id.toString()));
	}

	@Transactional
	public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword)) {
			throw new PasswordDoesNotMatchException();
		}
		
		User user = findById(id);
		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			throw new InvalidPasswordException();
		}
		
		user.setPassword(passwordEncoder.encode(newPassword));
		return user;
	}

	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(username.toString()));
	}

	@Transactional(readOnly = true)
	public User.Role findRoleByUsername(String username) {
		return userRepository.findRoleByUsername(username);
	}

}
