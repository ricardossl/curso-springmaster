package io.ricardosteel.vendas.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ricardosteel.vendas.domain.entity.Cliente;
import io.ricardosteel.vendas.domain.entity.ItemPedido;
import io.ricardosteel.vendas.domain.entity.Pedido;
import io.ricardosteel.vendas.domain.entity.Produto;
import io.ricardosteel.vendas.domain.enums.StatusPedido;
import io.ricardosteel.vendas.domain.repository.ClienteRepository;
import io.ricardosteel.vendas.domain.repository.PedidoRepository;
import io.ricardosteel.vendas.domain.repository.ProdutoRepository;
import io.ricardosteel.vendas.exceptions.RegistroNaoEncontradoException;
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
				.orElseThrow(() -> new RegistroNaoEncontradoException("Código de cliente inválido!"));

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setDataPedido(LocalDate.now());
		pedido.setTotal(pedidoDTO.getTotal());
		pedido.setItens(popularItens(pedidoDTO.getItens(), pedido));
		pedido.setStatusPedido(StatusPedido.REALIZADO);

		return pedidoRepository.save(pedido);
	}
	
	@Override
	public Optional<Pedido> getPedidoComplete(Integer id) {
		return pedidoRepository.findByIdFetchItem(id);
	}

	@Override
	@Transactional
	public void updateStatus(Integer id, StatusPedido statusPedido) {
		pedidoRepository.findById(id).map(pedido -> {
			pedido.setStatusPedido(statusPedido);
			return pedidoRepository.save(pedido);
		}).orElseThrow(() -> new RegistroNaoEncontradoException("Pedido não encontrado."));
	}

	private List<ItemPedido> popularItens(List<ItemPedidoDTO> listaItens, Pedido pedido) {
		if (listaItens.isEmpty())
			throw new RegraNegocioException("Não é possível realizar um pedido sem itens");

		return listaItens.stream().map(dto -> {
			Produto produto = produtoRepository.findById(dto.getProduto())
					.orElseThrow(() -> new RegistroNaoEncontradoException("Código de produto inválido!"));

			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(dto.getQuantidade());
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);

			return itemPedido;
		}).collect(Collectors.toList());
	}
	
}
