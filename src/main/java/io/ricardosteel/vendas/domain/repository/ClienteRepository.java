package io.ricardosteel.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ricardosteel.vendas.domain.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
