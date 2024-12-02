package com.github.jeanbezerra.ecommerce.web.portal.admin.controller;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.CategoriaDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Categoria;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class CategoriaController implements Serializable {

	private static final Logger LOGGER = LogManager.getLogger(CategoriaController.class);
	private static final long serialVersionUID = -2011360259755969920L;
	
	private CategoriaDAO categoriaDAO = new CategoriaDAO();
	private Categoria selectedCategoria = new Categoria();
	private List<Categoria> categorias;
	private boolean editMode = false;

	public CategoriaController() {
	}

	@PostConstruct
	public void init() {
		loadCategorias();
	}
	
	public void saveCategoria() {
		try {
			categoriaDAO.save(selectedCategoria);
			loadCategorias(); // Recarrega a lista de categorias
			resetForm(); // Reseta o formulário
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteCategoria(Long categoriaId) {
		try {
			categoriaDAO.delete(categoriaId);
			loadCategorias(); // Recarrega a lista de categorias
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareEdit(Categoria categoria) {
		this.selectedCategoria = categoriaDAO.findById(categoria.getCategoriaId());
		this.editMode = true;
	}

	private void loadCategorias() {
		this.categorias = categoriaDAO.findAll();
	}

	private void resetForm() {
		this.selectedCategoria = new Categoria();
		this.editMode = false;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public Categoria getSelectedCategoria() {
		return selectedCategoria;
	}

	public void setSelectedCategoria(Categoria selectedCategoria) {
		this.selectedCategoria = selectedCategoria;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
}