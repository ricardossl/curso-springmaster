package io.ricardosteel.vendas.serviceimpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.ricardosteel.vendas.domain.entity.Cliente;
import io.ricardosteel.vendas.domain.repository.ClienteRepository;
import io.ricardosteel.vendas.service.ClienteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
	private final ClienteRepository repository;

	@Override
	public Optional<Cliente> getClienteById(Integer id) {
		return repository.findById(id);
	}

}
