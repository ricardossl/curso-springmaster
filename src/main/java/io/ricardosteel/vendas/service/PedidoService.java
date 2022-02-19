package io.ricardosteel.vendas.service;

import io.ricardosteel.vendas.domain.entity.Pedido;
import io.ricardosteel.vendas.rest.dto.PedidoDTO;

public interface PedidoService {
	Pedido savePedido(PedidoDTO pedidoDTO);
}
