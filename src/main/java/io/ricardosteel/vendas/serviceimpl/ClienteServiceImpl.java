package io.ricardosteel.vendas.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import io.ricardosteel.vendas.domain.entity.Cliente;
import io.ricardosteel.vendas.domain.repository.ClienteRepository;
import io.ricardosteel.vendas.exceptions.RegraNegocioException;
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

	@Override
	public void saveCliente(Cliente cliente) {
		repository.save(cliente);
	}

	@Override
	public void deleteClient(Integer id) {
		Optional<Cliente> cliente = getClienteById(id);
		if (!cliente.isPresent()) {
			throw new RegraNegocioException("Cliente não encontrado para o id informado");
		}

		repository.delete(cliente.get());
	}

	@Override
	public Cliente updateCliente(Cliente clienteAtualizado, Integer id) {
		Optional<Cliente> cliente = getClienteById(id);
		if (!cliente.isPresent()) {
			throw new RegraNegocioException("Cliente não encontrado para o id informado");
		}

		clienteAtualizado.setId(cliente.get().getId());

		return repository.save(clienteAtualizado);
	}

	@Override
	public List<Cliente> findCliente(Cliente filtro) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING);

		Example<Cliente> example = Example.of(filtro, exampleMatcher);

		return repository.findAll(example);
	}

}
