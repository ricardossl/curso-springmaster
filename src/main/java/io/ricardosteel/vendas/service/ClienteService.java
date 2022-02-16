package io.ricardosteel.vendas.service;

import java.util.Optional;

import io.ricardosteel.vendas.domain.entity.Cliente;

public interface ClienteService {
	Optional<Cliente> getClienteById(Integer id);
	
	void saveCliente(Cliente cliente);
	
	void deleteClient(Integer id);
	
	Cliente updateCliente(Cliente clienteAtualizado, Integer id);
}
