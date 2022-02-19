package io.ricardosteel.vendas.serviceimpl;

import org.springframework.stereotype.Service;

import io.ricardosteel.vendas.domain.repository.PedidoRepository;
import io.ricardosteel.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
	private final PedidoRepository repository;

}
