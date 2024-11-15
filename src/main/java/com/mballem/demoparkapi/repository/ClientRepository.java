package com.mballem.demoparkapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mballem.demoparkapi.entity.Client;
import com.mballem.demoparkapi.repository.projection.ClientProjection;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("select c from Client c")
	Page<ClientProjection> findAllPageable(Pageable pageable);

	Client findByUserId(Long id);

	Optional<Client> findByCpf(String cpf);

}
