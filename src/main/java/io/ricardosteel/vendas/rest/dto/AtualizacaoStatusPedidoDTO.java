package io.ricardosteel.vendas.rest.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AtualizacaoStatusPedidoDTO {
	@NotNull(message = "{campo.novostatus.obrigatorio}")
	private String novoStatus;
}
