package com.mballem.demoparkapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoparkapi.entity.ClientSpot;
import com.mballem.demoparkapi.repository.projection.ClientSpotProjection;

public interface ClientSpotRepository extends JpaRepository<ClientSpot, Long> {

	Optional<ClientSpot> findByReceiptAndExitDateIsNull(String receipt);

	long countByClientCpfAndExitDateIsNotNull(String cpf);

	Page<ClientSpotProjection> findAllByClientCpf(String cpf, Pageable pageable);

}
