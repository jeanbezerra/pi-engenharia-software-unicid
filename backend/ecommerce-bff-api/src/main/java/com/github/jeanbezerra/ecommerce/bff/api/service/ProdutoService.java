package com.github.jeanbezerra.ecommerce.bff.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jeanbezerra.ecommerce.bff.api.entity.Produto;
import com.github.jeanbezerra.ecommerce.bff.api.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public ProdutoService() {
	}

	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}

	public Produto buscarPorId(Long id) {
		return produtoRepository.findById(id).orElse(null);
	}

	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	public void deletar(Long id) {
		produtoRepository.deleteById(id);
	}

}
