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

import com.github.jeanbezerra.ecommerce.bff.api.entity.Pedido;
import com.github.jeanbezerra.ecommerce.bff.api.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Operation(summary = "Lista todos os pedidos", description = "Retorna uma lista de todos os pedidos realizados.")
	@GetMapping
	public List<Pedido> listarTodos() {
		return pedidoService.listarTodos();
	}

	@Operation(summary = "Busca um pedido por ID", description = "Retorna um pedido específico baseado no ID fornecido.")
	@GetMapping("/{id}")
	public Pedido buscarPorId(@PathVariable Long id) {
		return pedidoService.buscarPorId(id);
	}

	@Operation(summary = "Cria um novo pedido", description = "Adiciona um novo pedido ao banco de dados.")
	@PostMapping
	public Pedido criarPedido(@RequestBody Pedido pedido) {
		return pedidoService.salvar(pedido);
	}

	@Operation(summary = "Deleta um pedido", description = "Remove um pedido específico do banco de dados baseado no ID fornecido.")
	@DeleteMapping("/{id}")
	public void deletarPedido(@PathVariable Long id) {
		pedidoService.deletar(id);
	}
}