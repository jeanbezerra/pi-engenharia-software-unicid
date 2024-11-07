package com.github.jeanbezerra.ecommerce.bff.api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pagamento_id", nullable = false, unique = true)
	private Long pagamentoId;

	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	private Pedido pedido;

	@Column(name = "tipo_pagamento", nullable = false)
	private String tipoPagamento;

	@Column(name = "status_pagamento", nullable = false)
	private String statusPagamento;

	@Column(name = "data_pagamento", nullable = false)
	private LocalDateTime dataPagamento;

	public Pagamento() {
		// TODO Auto-generated constructor stub
	}

	public Pagamento(Long pagamentoId, Pedido pedido, String tipoPagamento, String statusPagamento,
			LocalDateTime dataPagamento) {
		super();
		this.pagamentoId = pagamentoId;
		this.pedido = pedido;
		this.tipoPagamento = tipoPagamento;
		this.statusPagamento = statusPagamento;
		this.dataPagamento = dataPagamento;
	}

	public Long getPagamentoId() {
		return pagamentoId;
	}

	public void setPagamentoId(Long pagamentoId) {
		this.pagamentoId = pagamentoId;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public String getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(String statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	@Override
	public String toString() {
		return "Pagamento [pagamentoId=" + pagamentoId + ", pedido=" + pedido + ", tipoPagamento=" + tipoPagamento
				+ ", statusPagamento=" + statusPagamento + ", dataPagamento=" + dataPagamento + "]";
	}

}
