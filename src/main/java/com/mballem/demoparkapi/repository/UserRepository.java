package com.mballem.demoparkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoparkapi.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
