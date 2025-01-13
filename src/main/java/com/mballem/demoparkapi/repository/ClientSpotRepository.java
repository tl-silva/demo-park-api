package com.mballem.demoparkapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoparkapi.entity.ClientSpot;

public interface ClientSpotRepository extends JpaRepository<ClientSpot, Long> {

	Optional<ClientSpot> findByReceiptAndExitDateIsNull(String receipt);

	long countByClientCpfAndExitDateIsNotNull(String cpf);

}
