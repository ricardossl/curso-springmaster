package io.ricardosteel.vendas.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ricardosteel.vendas.domain.entity.Cliente;
import io.ricardosteel.vendas.exceptions.RegraNegocioException;
import io.ricardosteel.vendas.service.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
	private final ClienteService service;

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClientById(@PathVariable Integer id) {
		Optional<Cliente> cliente = service.getClienteById(id);

		if (cliente.isPresent())
			return ResponseEntity.ok(cliente.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<String> saveCliente(@RequestBody Cliente cliente) {
		try {
			service.saveCliente(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (RegraNegocioException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteClient(@PathVariable Integer id) {
		try {
			service.deleteClient(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (RegraNegocioException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@RequestBody Cliente clienteAtualizado, @PathVariable Integer id) {
		try {
			service.updateCliente(clienteAtualizado, id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (RegraNegocioException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> find(Cliente filtro) {
		List<Cliente> clientes = service.findCliente(filtro);

		if (clientes.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		return ResponseEntity.ok(clientes);
	}
}
