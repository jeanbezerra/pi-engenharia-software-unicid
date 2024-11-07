package com.github.jeanbezerra.ecommerce.bff.api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProdutoDTO {

	@NotNull(message = "O nome do produto é obrigatório")
	@JsonProperty("nome")
	private String nome;

	@JsonProperty("descricao")
	private String descricao;

	@NotNull(message = "O preço é obrigatório")
	@Min(value = 0, message = "O preço deve ser maior ou igual a zero")
	@JsonProperty("preco")
	private BigDecimal preco;

	@NotNull(message = "O estoque é obrigatório")
	@Min(value = 0, message = "O estoque deve ser maior ou igual a zero")
	@JsonProperty("estoque")
	private int estoque;

	@NotNull(message = "A imagem é obrigatória")
	@JsonProperty("imagem_url")
	private String imagemUrl;

	@NotNull(message = "O ID da categoria é obrigatório")
	@JsonProperty("categoria_id")
	private Long categoriaId;

	public ProdutoDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProdutoDTO(@NotNull(message = "O nome do produto é obrigatório") String nome, String descricao,
			@NotNull(message = "O preço é obrigatório") @Min(value = 0, message = "O preço deve ser maior ou igual a zero") BigDecimal preco,
			@NotNull(message = "O estoque é obrigatório") @Min(value = 0, message = "O estoque deve ser maior ou igual a zero") int estoque,
			@NotNull(message = "A imagem é obrigatória") String imagemUrl,
			@NotNull(message = "O ID da categoria é obrigatório") Long categoriaId) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.estoque = estoque;
		this.imagemUrl = imagemUrl;
		this.categoriaId = categoriaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

}