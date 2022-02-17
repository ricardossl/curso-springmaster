package io.ricardosteel.vendas.service;

import java.util.List;
import java.util.Optional;

import io.ricardosteel.vendas.domain.entity.Produto;

public interface ProdutoService {
	Optional<Produto> getProdutoById(Integer id);

	Produto saveProduto(Produto produto);

	Produto updateProduto(Produto produtoUpdate, Integer id);

	void deleteProduto(Integer id);

	List<Produto> findProdutosFilter(Produto filter);
}
