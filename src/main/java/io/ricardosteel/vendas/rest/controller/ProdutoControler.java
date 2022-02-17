package io.ricardosteel.vendas.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ricardosteel.vendas.domain.entity.Produto;
import io.ricardosteel.vendas.exceptions.RegraNegocioException;
import io.ricardosteel.vendas.service.ProdutoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoControler {
	private final ProdutoService service;

	@GetMapping("{id}")
	public ResponseEntity<Produto> getProdutoById(@PathVariable Integer id) {
		Optional<Produto> produto = service.getProdutoById(id);
		if (produto.isPresent()) {
			return ResponseEntity.ok(produto.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Produto produto) {
		try {
			return ResponseEntity.ok(service.saveProduto(produto));
		} catch (RegraNegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> update(@RequestBody Produto produto, @PathVariable Integer id) {
		try {
			return ResponseEntity.ok(service.updateProduto(produto, id));
		} catch (RegraNegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		try {
			service.deleteProduto(id);
			return ResponseEntity.noContent().build();
		} catch (RegraNegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Produto>> findProdutoFilter(@RequestBody Produto produto) {
		return ResponseEntity.ok(service.findProdutosFilter(produto));
	}
}
