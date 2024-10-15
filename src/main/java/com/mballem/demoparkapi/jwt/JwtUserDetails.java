package com.mballem.demoparkapi.jwt;

import org.springframework.security.core.authority.AuthorityUtils;

import com.mballem.demoparkapi.entity.User;

public class JwtUserDetails extends org.springframework.security.core.userdetails.User  {
	
	private User user;

	public JwtUserDetails(User user) {
		super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
		this.user = user;
	}
	
	public Long getId() {
		return this.user.getId();
	}
	
	public String getRole() {
		return this.user.getRole().name();
	}

}
