package io.ricardosteel.vendas.service;

import java.util.List;
import java.util.Optional;

import io.ricardosteel.vendas.domain.entity.Cliente;

public interface ClienteService {
	Cliente getClienteById(Integer id);

	Optional<Cliente> getClienteByIdOptional(Integer id);

	void saveCliente(Cliente cliente);

	void deleteClient(Integer id);

	void updateCliente(Cliente clienteAtualizado, Integer id);

	List<Cliente> findCliente(Cliente filtro);
}
