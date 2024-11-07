package com.github.jeanbezerra.ecommerce.bff.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jeanbezerra.ecommerce.bff.api.dto.ProdutoDTO;
import com.github.jeanbezerra.ecommerce.bff.api.entity.Categoria;
import com.github.jeanbezerra.ecommerce.bff.api.entity.Produto;
import com.github.jeanbezerra.ecommerce.bff.api.service.CategoriaService;
import com.github.jeanbezerra.ecommerce.bff.api.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private CategoriaService categoriaService;

	@Operation(summary = "Lista todos os produtos", description = "Retorna uma lista de todos os produtos disponíveis.")
	@GetMapping
	public List<Produto> listarTodos() {
		return produtoService.listarTodos();
	}

	@Operation(summary = "Busca um produto por ID", description = "Retorna um produto específico baseado no ID fornecido.")
	@GetMapping("/{id}")
	public Produto buscarPorId(@PathVariable Long id) {
		return produtoService.buscarPorId(id);
	}

	@Operation(summary = "Cria um novo produto", description = "Adiciona um novo produto ao banco de dados.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Exemplo de Produto", value = "{ \"nome\": \"Cupcake de Chocolate\", \"descricao\": \"Cupcake delicioso com cobertura de chocolate\", \"preco\": 12.50, \"estoque\": 100, \"imagemUrl\": \"http://example.com/cupcake.jpg\", \"categoria_id\": 1 }"))))
	@PostMapping
	public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
		if (produtoDTO.getCategoriaId() == null) {
			return ResponseEntity.badRequest().body(Map.of("message", "O ID da categoria é obrigatório"));
		}

		Categoria categoria = categoriaService.buscarPorId(produtoDTO.getCategoriaId());
		if (categoria == null) {
			return ResponseEntity.status(404).body(Map.of("message", "Categoria com o ID fornecido não encontrada"));
		}

		Produto produto = new Produto();
		produto.setNome(produtoDTO.getNome());
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setPreco(produtoDTO.getPreco());
		produto.setEstoque(produtoDTO.getEstoque());
		produto.setImagemUrl(produtoDTO.getImagemUrl());
		produto.setCategoria(categoria);

		Produto produtoSalvo = produtoService.salvar(produto);
		return ResponseEntity.status(201).body(produtoSalvo);
	}

	@Operation(summary = "Deleta um produto", description = "Remove um produto específico do banco de dados baseado no ID fornecido.")
	@DeleteMapping("/{id}")
	public void deletarProduto(@PathVariable Long id) {
		produtoService.deletar(id);
	}
}
