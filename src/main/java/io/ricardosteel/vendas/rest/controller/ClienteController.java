package io.ricardosteel.vendas.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.ricardosteel.vendas.domain.entity.Cliente;
import io.ricardosteel.vendas.service.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
	private final ClienteService service;

	@GetMapping("/{id}")
	public Cliente getClientById(@PathVariable Integer id) {
		return service.getClienteById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveCliente(@RequestBody Cliente cliente) {
		service.saveCliente(cliente);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteClient(@PathVariable Integer id) {
		service.deleteClient(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Cliente clienteAtualizado, @PathVariable Integer id) {
		service.updateCliente(clienteAtualizado, id);
	}

	@GetMapping
	public List<Cliente> find(@RequestBody Cliente filtro) {
		return service.findCliente(filtro);
	}
}
