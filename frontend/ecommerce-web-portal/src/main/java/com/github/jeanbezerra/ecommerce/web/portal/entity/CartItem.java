package com.github.jeanbezerra.ecommerce.web.portal.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.github.jeanbezerra.ecommerce.web.portal.dto.ProdutoDTO;

public class CartItem implements Serializable {

	private static final long serialVersionUID = 496828066502918757L;

	private ProdutoDTO product; // Produto associado ao item
	private Integer quantity; // Quantidade do produto no carrinho
	private BigDecimal unitPrice; // Preço unitário do produto

	public CartItem() {
		// TODO Auto-generated constructor stub
	}
	
	public CartItem(ProdutoDTO product, Integer quantity, BigDecimal unitPrice) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	
	// Calcula o subtotal
    public BigDecimal getSubtotal() {
        if (unitPrice == null || quantity <= 0) {
            return BigDecimal.ZERO;
        }
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

	public ProdutoDTO getProduct() {
		return product;
	}

	public void setProduct(ProdutoDTO product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}