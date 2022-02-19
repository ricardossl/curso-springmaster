package io.ricardosteel.vendas.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ricardosteel.vendas.domain.entity.Cliente;
import io.ricardosteel.vendas.domain.entity.ItemPedido;
import io.ricardosteel.vendas.domain.entity.Pedido;
import io.ricardosteel.vendas.domain.entity.Produto;
import io.ricardosteel.vendas.domain.repository.ClienteRepository;
import io.ricardosteel.vendas.domain.repository.PedidoRepository;
import io.ricardosteel.vendas.domain.repository.ProdutoRepository;
import io.ricardosteel.vendas.exceptions.RegraNegocioException;
import io.ricardosteel.vendas.rest.dto.ItemPedidoDTO;
import io.ricardosteel.vendas.rest.dto.PedidoDTO;
import io.ricardosteel.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
	private final PedidoRepository pedidoRepository;
	private final ClienteRepository clienteRepository;
	private final ProdutoRepository produtoRepository;

	@Override
	@Transactional
	public Pedido savePedido(PedidoDTO pedidoDTO) {
		Cliente cliente = clienteRepository.findById(pedidoDTO.getCliente())
				.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido!"));

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setDataPedido(LocalDate.now());
		pedido.setTotal(pedidoDTO.getTotal());
		pedido.setItensPedido(popularItens(pedidoDTO.getItens(), pedido));

		return pedidoRepository.save(pedido);
	}

	private List<ItemPedido> popularItens(List<ItemPedidoDTO> listaItens, Pedido pedido) {
		if (listaItens.isEmpty())
			throw new RegraNegocioException("Não é possível realizar um pedido sem itens");

		return listaItens.stream().map(dto -> {
			Produto produto = produtoRepository.findById(dto.getProduto())
					.orElseThrow(() -> new RegraNegocioException("Código de produto inválido!"));

			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(dto.getQuantidade());
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);

			return itemPedido;
		}).collect(Collectors.toList());
	}
}
