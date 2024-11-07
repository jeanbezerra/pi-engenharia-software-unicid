package com.github.jeanbezerra.ecommerce.bff.api.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pedido_id", nullable = false, unique = true)
	private Long pedidoId;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@Column(nullable = false)
	private LocalDateTime dataPedido;

	@Column(nullable = false)
	private String status;

	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;

	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	public Pedido(Long pedidoId, Cliente cliente, LocalDateTime dataPedido, String status, List<ItemPedido> itens) {
		super();
		this.pedidoId = pedidoId;
		this.cliente = cliente;
		this.dataPedido = dataPedido;
		this.status = status;
		this.itens = itens;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "Pedido [pedidoId=" + pedidoId + ", cliente=" + cliente + ", dataPedido=" + dataPedido + ", status="
				+ status + ", itens=" + itens + "]";
	}

}
