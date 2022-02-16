package io.ricardosteel.vendas.rest.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ricardosteel.vendas.domain.entity.Cliente;
import io.ricardosteel.vendas.service.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
	private final ClienteService clienteServiceImpl;

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClientById(@PathVariable Integer id) {
		Optional<Cliente> cliente = clienteServiceImpl.getClienteById(id);

		if (cliente.isPresent())
			return ResponseEntity.ok(cliente.get());

		return ResponseEntity.notFound().build();
	}

}
