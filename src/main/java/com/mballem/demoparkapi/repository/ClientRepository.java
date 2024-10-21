package com.mballem.demoparkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoparkapi.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
