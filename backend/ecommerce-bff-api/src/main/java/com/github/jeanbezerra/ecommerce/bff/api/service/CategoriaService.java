package com.github.jeanbezerra.ecommerce.bff.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jeanbezerra.ecommerce.bff.api.entity.Categoria;
import com.github.jeanbezerra.ecommerce.bff.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
    private CategoriaRepository categoriaRepository;
	
	public CategoriaService() {
	}

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deletar(Long id) {
        categoriaRepository.deleteById(id);
    }

}
