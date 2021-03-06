package io.ricardosteel.vendas.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.ricardosteel.vendas.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
	@NotNull(message = "{campo.cliente.obrigatorio}")
	private Integer cliente;

	@NotNull(message = "{campo.total.obrigatorio}")
	private BigDecimal total;

	@NotEmptyList(message = "{campo.itens.obrigatorio}")
	private List<ItemPedidoDTO> itens;
}
