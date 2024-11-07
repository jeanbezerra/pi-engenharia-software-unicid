package com.github.jeanbezerra.ecommerce.bff.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jeanbezerra.ecommerce.bff.api.entity.Pedido;
import com.github.jeanbezerra.ecommerce.bff.api.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public PedidoService() {
	}

	public List<Pedido> listarTodos() {
		return pedidoRepository.findAll();
	}

	public Pedido buscarPorId(Long id) {
		return pedidoRepository.findById(id).orElse(null);
	}

	public Pedido salvar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	public void deletar(Long id) {
		pedidoRepository.deleteById(id);
	}
}