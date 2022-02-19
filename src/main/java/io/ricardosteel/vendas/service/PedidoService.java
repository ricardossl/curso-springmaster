package io.ricardosteel.vendas.service;

import java.util.Optional;

import io.ricardosteel.vendas.domain.entity.Pedido;
import io.ricardosteel.vendas.rest.dto.PedidoDTO;

public interface PedidoService {
	Pedido savePedido(PedidoDTO pedidoDTO);
	
	Optional<Pedido> getPedidoComplete(Integer id);
}
