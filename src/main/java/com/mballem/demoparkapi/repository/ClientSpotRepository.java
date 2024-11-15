package com.mballem.demoparkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoparkapi.entity.ClientSpot;

public interface ClientSpotRepository extends JpaRepository<ClientSpot, Long> {

}
