package com.github.jeanbezerra.ecommerce.bff.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.jeanbezerra.ecommerce.bff.api.entity.Pedido;

public interface PedidoRepository  extends JpaRepository<Pedido, Long>{

}