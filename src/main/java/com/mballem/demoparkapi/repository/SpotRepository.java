package com.mballem.demoparkapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoparkapi.entity.Spot;

public interface SpotRepository extends JpaRepository<Spot, Long> {

	Optional<Spot> findByCode(String code);

}
