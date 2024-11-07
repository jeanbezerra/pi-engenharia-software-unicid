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

import com.github.jeanbezerra.ecommerce.bff.api.entity.Categoria;
import com.github.jeanbezerra.ecommerce.bff.api.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@Operation(summary = "Lista todas as categorias", description = "Retorna uma lista de todas as categorias disponíveis.")
	@GetMapping
	public List<Categoria> listarTodas() {
		return categoriaService.listarTodas();
	}

	@Operation(summary = "Busca uma categoria por ID", description = "Retorna uma categoria específica baseada no ID fornecido. Retorna 204 (No Content) se a categoria não for encontrada.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "204", description = "Nenhuma registro encontrada com o ID fornecido", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "Failure", content = @Content(mediaType = "application/json")) })
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Categoria categoria = categoriaService.buscarPorId(id);
		if (categoria == null) {
			return ResponseEntity.status(204)
					.body(Map.of("message", "Nenhuma categoria encontrada com o ID fornecido").toString());
		} else {
			return ResponseEntity.ok(categoria);
		}
	}

	@Operation(summary = "Cria uma nova categoria", description = "Adiciona uma nova categoria ao banco de dados.")
	@PostMapping(consumes = "application/json", produces = "application/json")
	public Categoria criarCategoria(@RequestBody Categoria categoria) {
		return categoriaService.salvar(categoria);
	}

	@Operation(summary = "Deleta uma categoria", description = "Remove uma categoria específica do banco de dados baseado no ID fornecido.")
	@DeleteMapping("/{id}")
	public void deletarCategoria(@PathVariable Long id) {
		categoriaService.deletar(id);
	}
}