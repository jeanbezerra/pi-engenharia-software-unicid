package com.github.jeanbezerra.ecommerce.web.portal.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 9047735747159607281L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoria_id", nullable = false, unique = true)
	@JsonProperty(value = "categoria_id")
	private Long categoriaId;

	@Column(name = "nome", nullable = false)
	@JsonProperty(value = "nome")
	private String nome;

	public Categoria() {
	}

	public Categoria(Long categoriaId, String nome) {
		super();
		this.categoriaId = categoriaId;
		this.nome = nome;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Categoria [categoriaId=" + categoriaId + ", nome=" + nome + "]";
	}

}
