package com.github.jeanbezerra.ecommerce.bff.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.jeanbezerra.ecommerce.bff.api.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

}
