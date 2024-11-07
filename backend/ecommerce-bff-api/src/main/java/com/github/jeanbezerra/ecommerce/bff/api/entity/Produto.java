package com.github.jeanbezerra.ecommerce.bff.api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "produto_id", unique = true)
	private Long produtoId;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "preco", nullable = false)
	private BigDecimal preco;

	@Column(name = "estoque", nullable = false)
	private int estoque;

	@Column(name = "imagem_url")
	private String imagemUrl;

	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private Categoria categoria;

	public Produto() {
		// TODO Auto-generated constructor stub
	}

	public Produto(Long produtoId, String nome, String descricao, BigDecimal preco, int estoque, String imagemUrl,
			Categoria categoria) {
		super();
		this.produtoId = produtoId;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.estoque = estoque;
		this.imagemUrl = imagemUrl;
		this.categoria = categoria;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Produto [produtoId=" + produtoId + ", nome=" + nome + ", descricao=" + descricao + ", preco=" + preco
				+ ", estoque=" + estoque + ", imagemUrl=" + imagemUrl + ", categoria=" + categoria + "]";
	}

}
