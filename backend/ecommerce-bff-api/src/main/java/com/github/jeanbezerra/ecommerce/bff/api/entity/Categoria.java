package com.github.jeanbezerra.ecommerce.bff.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

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
