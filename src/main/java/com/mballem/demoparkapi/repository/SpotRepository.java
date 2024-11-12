package com.mballem.demoparkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoparkapi.entity.Spot;

public interface SpotRepository extends JpaRepository<Spot, Long> {

}
