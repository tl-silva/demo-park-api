package com.mballem.demoparkapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.Client;
import com.mballem.demoparkapi.exception.CpfUniqueViolationException;
import com.mballem.demoparkapi.repository.ClientRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientService {
	
	private final ClientRepository clientRepository;
	
	@Transactional
	public Client save(Client client) {
		try {
			return clientRepository.save(client);
		} catch(DataIntegrityViolationException ex) {
			throw new CpfUniqueViolationException(
					String.format("CPF '%s' already registered", client.getCpf()));
		}
	}

	@Transactional(readOnly = true)
	public Client findById(Long id) {
		return clientRepository.findById(id).orElseThrow(
		() -> new EntityNotFoundException(String.format("Client id=%s not found", id))
		);
	}

}
