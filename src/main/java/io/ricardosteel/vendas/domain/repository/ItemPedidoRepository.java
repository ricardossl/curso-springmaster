package io.ricardosteel.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ricardosteel.vendas.domain.entity.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
