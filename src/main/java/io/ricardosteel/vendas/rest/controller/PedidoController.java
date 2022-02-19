package io.ricardosteel.vendas.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.ricardosteel.vendas.domain.entity.ItemPedido;
import io.ricardosteel.vendas.domain.entity.Pedido;
import io.ricardosteel.vendas.domain.enums.StatusPedido;
import io.ricardosteel.vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import io.ricardosteel.vendas.rest.dto.InformacaoItemPedidoDTO;
import io.ricardosteel.vendas.rest.dto.InformacoesPedidoDTO;
import io.ricardosteel.vendas.rest.dto.PedidoDTO;
import io.ricardosteel.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
	private final PedidoService service;

	@PostMapping
	public Integer save(@RequestBody @Valid PedidoDTO pedidoDTO) {
		return service.savePedido(pedidoDTO).getId();
	}

	@GetMapping("/{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return service.getPedidoComplete(id).map(p -> converterPedido(p))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!"));
	}

	@PatchMapping("/{id}")
	public void updateStatus(@RequestBody @Valid AtualizacaoStatusPedidoDTO atualizacaoStatusPedidoDTO,
			@PathVariable Integer id) {
		service.updateStatus(id, StatusPedido.valueOf(atualizacaoStatusPedidoDTO.getNovoStatus()));
	}

	private InformacoesPedidoDTO converterPedido(Pedido pedido) {
		return InformacoesPedidoDTO.builder().codigo(pedido.getId())
				.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.cpf(pedido.getCliente().getCpf()).nomeCliente(pedido.getCliente().getNome()).total(pedido.getTotal())
				.status(pedido.getStatusPedido().name()).itens(converterItemPedido(pedido.getItens())).build();
	}

	private List<InformacaoItemPedidoDTO> converterItemPedido(List<ItemPedido> listaItens) {
		if (listaItens.isEmpty())
			return Collections.emptyList();

		return listaItens.stream()
				.map(item -> InformacaoItemPedidoDTO.builder().descricaoProduto(item.getProduto().getDescricao())
						.precoUnitario(item.getProduto().getPreco()).quantidade(item.getQuantidade()).build())
				.collect(Collectors.toList());
	}
}
