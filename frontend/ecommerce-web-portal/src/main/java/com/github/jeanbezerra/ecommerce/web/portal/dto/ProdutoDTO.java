package com.github.jeanbezerra.ecommerce.web.portal.dto;

import java.io.Serializable;

public class ProdutoDTO implements Serializable{
	
	private static final long serialVersionUID = 3783913707644044450L;
	
	private Long id;
	private String nome;
	private String descricao;
	private Double preco;
	private Integer estoque;
	private String imagemUrl;
	private String categoria;

	public ProdutoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ProdutoDTO(Long id, String nome, String descricao, Double preco, Integer estoque, String imagemUrl,
			String categoria) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.estoque = estoque;
		this.imagemUrl = imagemUrl;
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
