package com.github.jeanbezerra.ecommerce.bff.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jeanbezerra.ecommerce.bff.api.entity.Pagamento;
import com.github.jeanbezerra.ecommerce.bff.api.repository.PagamentoRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	public PagamentoService() {
	}

	public List<Pagamento> listarTodos() {
		return pagamentoRepository.findAll();
	}

	public Pagamento buscarPorId(Long id) {
		return pagamentoRepository.findById(id).orElse(null);
	}

	public Pagamento salvar(Pagamento pagamento) {
		return pagamentoRepository.save(pagamento);
	}

	public void deletar(Long id) {
		pagamentoRepository.deleteById(id);
	}
}