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
	public Cliente getClienteById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Cliente não encontrado para o id informado"));
	}

	@Override
	public void saveCliente(Cliente cliente) {
		repository.save(cliente);
	}

	@Override
	public void deleteClient(Integer id) {
		getClienteByIdOptional(id).map(cliente -> {
			repository.delete(cliente);
			return Void.TYPE;
		}).orElseThrow(() -> new RegraNegocioException("Não foi possível deletar o cliente"));
	}

	@Override
	public void updateCliente(Cliente clienteAtualizado, Integer id) {
		getClienteByIdOptional(id).map(cliente -> {
			clienteAtualizado.setId(cliente.getId());

			return repository.save(clienteAtualizado);
		}).orElseThrow(() -> new RegraNegocioException("Não foi possível atualizar o cliente"));
	}

	@Override
	public List<Cliente> findCliente(Cliente filtro) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING);

		Example<Cliente> example = Example.of(filtro, exampleMatcher);

		return repository.findAll(example);
	}

	@Override
	public Optional<Cliente> getClienteByIdOptional(Integer id) {
		return Optional.of(repository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Cliente não encontrado para o id informado")));
	}

}
