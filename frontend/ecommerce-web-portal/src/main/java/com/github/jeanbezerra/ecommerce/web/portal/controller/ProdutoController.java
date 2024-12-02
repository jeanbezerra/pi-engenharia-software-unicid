package com.github.jeanbezerra.ecommerce.web.portal.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.jeanbezerra.ecommerce.web.portal.dao.ProdutoDAO;
import com.github.jeanbezerra.ecommerce.web.portal.dto.ProdutoDTO;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ProdutoController implements Serializable {

	private static final long serialVersionUID = -378137317794513163L;

	protected ProdutoDAO produtoDAO = new ProdutoDAO();

	private List<ProdutoDTO> produtosList = new ArrayList<>();

	private ProdutoDTO selectedProduct = new ProdutoDTO();

	public ProdutoController() {
		reloadAll();
	}

	public void reloadAll() {
		try {

			this.produtosList.clear();
			this.produtosList = this.produtoDAO.buscarProdutosComCategoria();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadProduct() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String productIdParam = params.get("produtoId");

			if (productIdParam != null && !productIdParam.isEmpty()) {
				Long productId = Long.parseLong(productIdParam);
				this.selectedProduct = produtoDAO.buscarProdutoPorId(productId);
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("home.jsf?faces-redirect=true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("home.jsf?faces-redirect=true");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<ProdutoDTO> getProdutosList() {
		return produtosList;
	}

	public void setProdutosList(List<ProdutoDTO> produtosList) {
		this.produtosList = produtosList;
	}

	public ProdutoDTO getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(ProdutoDTO selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

}
