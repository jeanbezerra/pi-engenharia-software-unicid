package com.github.jeanbezerra.ecommerce.bff.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.jeanbezerra.ecommerce.bff.api.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
