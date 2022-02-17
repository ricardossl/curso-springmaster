package io.ricardosteel.vendas.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import io.ricardosteel.vendas.domain.entity.Produto;
import io.ricardosteel.vendas.domain.repository.ProdutoRepository;
import io.ricardosteel.vendas.exceptions.RegraNegocioException;
import io.ricardosteel.vendas.service.ProdutoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {
	private final ProdutoRepository repository;

	@Override
	public Optional<Produto> getProdutoById(Integer id) {
		Optional<Produto> produto = repository.findById(id);
		if (!produto.isPresent()) {
			throw new RegraNegocioException("Produto não encontrado para o id informado");
		}

		return produto;
	}

	@Override
	public Produto saveProduto(Produto produto) {
		return repository.save(produto);
	}

	@Override
	public Produto updateProduto(Produto produtoUpdate, Integer id) {
		return getProdutoById(id).map(produto -> {
			produtoUpdate.setId(produto.getId());
			return repository.save(produtoUpdate);
		}).orElseThrow(() -> new RegraNegocioException("Não foi possível atualizar o produto."));
	}

	@Override
	public void deleteProduto(Integer id) {
		getProdutoById(id).map(produto -> {
			repository.delete(produto);
			return Void.TYPE;
		}).orElseThrow(() -> new RegraNegocioException("Não foi possível remover o produto."));
	}

	@Override
	public List<Produto> findProdutosFilter(Produto filter) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
		Example<Produto> example = Example.of(filter, matcher);

		return repository.findAll(example);
	}

}
