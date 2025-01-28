package com.mballem.demoparkapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.Client;
import com.mballem.demoparkapi.exception.ClientCpfNotFoundException;
import com.mballem.demoparkapi.exception.ClientIdNotFoundException;
import com.mballem.demoparkapi.exception.CpfUniqueViolationException;
import com.mballem.demoparkapi.repository.ClientRepository;
import com.mballem.demoparkapi.repository.projection.ClientProjection;

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
			throw new CpfUniqueViolationException("CPF", client.getCpf());
		}
	}

	@Transactional(readOnly = true)
	public Client findById(Long id) {
		return clientRepository.findById(id).orElseThrow(
		() -> new ClientIdNotFoundException(id.toString()));
	}

	@Transactional(readOnly = true)
	public Page<ClientProjection> findAll(Pageable pageable) {
		return clientRepository.findAllPageable(pageable);
	}
	
	@Transactional(readOnly = true)
	public Client findByUserId(Long id) {
		return clientRepository.findByUserId(id);
	}

	@Transactional(readOnly = true)
	public Client findByCpf(String cpf) {
		return clientRepository.findByCpf(cpf).orElseThrow(
				() -> new ClientCpfNotFoundException(cpf));
	}

}
