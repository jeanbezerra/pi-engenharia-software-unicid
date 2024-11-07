package com.github.jeanbezerra.ecommerce.bff.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jeanbezerra.ecommerce.bff.api.entity.Cliente;
import com.github.jeanbezerra.ecommerce.bff.api.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public ClienteService() {
	}

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public void deletar(Long id) {
		clienteRepository.deleteById(id);
	}
}