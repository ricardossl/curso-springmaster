package io.ricardosteel.vendas.rest.controller;

import java.util.List;

import javax.validation.Valid;

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

import io.ricardosteel.vendas.domain.entity.Cliente;
import io.ricardosteel.vendas.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Api("API Clientes")
public class ClienteController {
	private final ClienteService service;

	@GetMapping("/{id}")
	@ApiOperation("Obter um detalhe de um cliente")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cliente encontrado"),
			@ApiResponse(code = 400, message = "Cliente não encontrado para o ID informado") })
	public Cliente getClientById(@PathVariable Integer id) {
		return service.getClienteById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveCliente(@RequestBody @Valid Cliente cliente) {
		service.saveCliente(cliente);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteClient(@PathVariable Integer id) {
		service.deleteClient(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody @Valid Cliente clienteAtualizado, @PathVariable Integer id) {
		service.updateCliente(clienteAtualizado, id);
	}

	@GetMapping
	public List<Cliente> find(Cliente filtro) {
		return service.findCliente(filtro);
	}
}
