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

import com.github.jeanbezerra.ecommerce.bff.api.entity.Pagamento;
import com.github.jeanbezerra.ecommerce.bff.api.service.PagamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pagamentos", description = "Endpoints para gerenciamento de pagamentos")
@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

	@Autowired
	private PagamentoService pagamentoService;

	@Operation(summary = "Lista todos os pagamentos", description = "Retorna uma lista de todos os pagamentos realizados.")
	@GetMapping
	public List<Pagamento> listarTodos() {
		return pagamentoService.listarTodos();
	}

	@Operation(summary = "Busca um pagamento por ID", description = "Retorna um pagamento específico baseado no ID fornecido.")
	@GetMapping("/{id}")
	public Pagamento buscarPorId(@PathVariable Long id) {
		return pagamentoService.buscarPorId(id);
	}

	@Operation(summary = "Cria um novo pagamento", description = "Adiciona um novo pagamento ao banco de dados.")
	@PostMapping
	public Pagamento criarPagamento(@RequestBody Pagamento pagamento) {
		return pagamentoService.salvar(pagamento);
	}

	@Operation(summary = "Deleta um pagamento", description = "Remove um pagamento específico do banco de dados baseado no ID fornecido.")
	@DeleteMapping("/{id}")
	public void deletarPagamento(@PathVariable Long id) {
		pagamentoService.deletar(id);
	}
}