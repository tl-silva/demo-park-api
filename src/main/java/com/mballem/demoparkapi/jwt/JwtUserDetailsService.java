package com.mballem.demoparkapi.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mballem.demoparkapi.entity.User;
import com.mballem.demoparkapi.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	private final UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user  = userService.findByUsername(username);
		return new JwtUserDetails(user);
	}
	
	public JwtToken getTokenAuthenticated(String username) {
		User.Role role = userService.findRoleByUsername(username);
		return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
	}

}
