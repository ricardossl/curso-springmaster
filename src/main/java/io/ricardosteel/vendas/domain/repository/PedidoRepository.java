package io.ricardosteel.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ricardosteel.vendas.domain.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
