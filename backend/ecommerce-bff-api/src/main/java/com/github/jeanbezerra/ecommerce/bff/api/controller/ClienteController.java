package com.github.jeanbezerra.ecommerce.bff.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jeanbezerra.ecommerce.bff.api.entity.Cliente;
import com.github.jeanbezerra.ecommerce.bff.api.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Operation(summary = "Lista todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados.")
	@GetMapping
	public List<Cliente> listarTodos() {
		return clienteService.listarTodos();
	}

	@Operation(summary = "Busca um cliente por ID", description = "Retorna um cliente específico baseado no ID fornecido.")
	@GetMapping("/{id}")
	public Cliente buscarPorId(@PathVariable Long id) {
		return clienteService.buscarPorId(id);
	}

	@Operation(summary = "Cria um novo cliente", description = "Adiciona um novo cliente ao banco de dados.")
	@PostMapping
	public Cliente criarCliente(@RequestBody Cliente cliente) {
		return clienteService.salvar(cliente);
	}

	@Operation(summary = "Deleta um cliente", description = "Remove um cliente específico do banco de dados baseado no ID fornecido.")
	@DeleteMapping("/{id}")
	public void deletarCliente(@PathVariable Long id) {
		clienteService.deletar(id);
	}
}