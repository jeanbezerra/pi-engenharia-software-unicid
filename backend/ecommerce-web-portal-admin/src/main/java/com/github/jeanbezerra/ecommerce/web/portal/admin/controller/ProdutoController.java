package com.github.jeanbezerra.ecommerce.web.portal.admin.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.CategoriaDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.ProdutoDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Categoria;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Produto;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(value = "produtoController")
@SessionScoped
public class ProdutoController implements Serializable {

	private static final Logger LOGGER = LogManager.getLogger(ProdutoController.class);
	private static final long serialVersionUID = -212801482036039330L;

	@Inject
	protected ProdutoDAO produtoDAO = new ProdutoDAO();

	@Inject
	protected CategoriaDAO categoriaDAO = new CategoriaDAO();

	@Inject
	private Produto selectedProduto;
	
	private Long selectedCategoryId;

	private List<Produto> produtos;
	private List<Categoria> categorias; // Para o dropdown
	private boolean editMode = false;

	private List<SelectItem> categoriaOptions = new ArrayList<>();

	public ProdutoController() {

	}

	@PostConstruct
	public void init() {

	}

	public void saveProduto() {
		try {		

			this.selectedProduto.setCategoria(this.categoriaDAO.findById(this.selectedCategoryId));
			produtoDAO.save(selectedProduto);
			loadProdutos(); // Recarrega a lista de produtos
			resetForm(); // Reseta o formulário
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteProduto(Long produtoId) {
		try {
			produtoDAO.delete(produtoId);
			loadProdutos(); // Recarrega a lista de produtos
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareEdit(Produto produto) {
		this.selectedProduto = produtoDAO.findById(produto.getProdutoId());
		this.selectedCategoryId = this.selectedProduto.getCategoria().getCategoriaId();
		this.editMode = true;
	}

	private void loadProdutos() {
		this.produtos = produtoDAO.findAll();
	}

	private void loadCategorias() {
		this.categorias = categoriaDAO.findAll();
		this.categoriaOptions = this.categoriaDAO.findAllAsSelectItems();
	}

	private void resetForm() {
		this.selectedProduto = new Produto();
		this.editMode = false;
	}

	public void loadPage() {
		// Verifica se a requisição é uma renderização inicial (GET)
		if (!FacesContext.getCurrentInstance().isPostback()) {
			loadProdutos(); // Carrega os produtos apenas na renderização inicial
			loadCategorias(); // Atualiza as categorias

			resetForm();
		}
	}

	public Produto getSelectedProduto() {
		return selectedProduto;
	}

	public void setSelectedProduto(Produto selectedProduto) {
		this.selectedProduto = selectedProduto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<SelectItem> getCategoriaOptions() {
		return categoriaOptions;
	}

	public void setCategoriaOptions(List<SelectItem> categoriaOptions) {
		this.categoriaOptions = categoriaOptions;
	}

	public Long getSelectedCategoryId() {
		return selectedCategoryId;
	}

	public void setSelectedCategoryId(Long selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
	}



	
}