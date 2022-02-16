package io.ricardosteel.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ricardosteel.vendas.domain.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
