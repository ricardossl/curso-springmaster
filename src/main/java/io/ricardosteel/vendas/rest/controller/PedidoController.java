package io.ricardosteel.vendas.rest.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ricardosteel.vendas.rest.dto.PedidoDTO;
import io.ricardosteel.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
	private final PedidoService service;

	@PostMapping
	Integer save(@RequestBody @Valid PedidoDTO pedidoDTO) {
		return service.savePedido(pedidoDTO).getId();
	}
}
