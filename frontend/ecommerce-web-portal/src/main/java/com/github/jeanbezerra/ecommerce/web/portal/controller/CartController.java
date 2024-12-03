package com.github.jeanbezerra.ecommerce.web.portal.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.github.jeanbezerra.ecommerce.web.portal.dto.ProdutoDTO;
import com.github.jeanbezerra.ecommerce.web.portal.entity.CartItem;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class CartController implements Serializable {

    private static final long serialVersionUID = 1L;

    // Lista de itens no carrinho
    private List<CartItem> cartItems = new ArrayList<>();

    // Total do carrinho
    private BigDecimal total = BigDecimal.ZERO;

    // Quantidade a adicionar
    private int quantity = 1;

    /**
     * Adiciona um item ao carrinho.
     *
     * @param product Produto a ser adicionado
     */
    public void addToCart(ProdutoDTO produto) {
        // Verifica se o produto já está no carrinho
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(produto.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                recalculateTotal();
                return;
            }
        }

        // Se não estiver no carrinho, adiciona como novo item
        CartItem newItem = new CartItem();
        
        newItem.setProduct(produto);
        newItem.setQuantity(this.quantity);        
        newItem.setUnitPrice(new BigDecimal(produto.getPreco()));
        cartItems.add(newItem);
        recalculateTotal();
    }
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public void removeItemMessage() {
        addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Item removido do carrinho com sucesso");
    }
    public void removeAllItens() {
        addMessage(FacesMessage.SEVERITY_INFO, "Informação", "Carrinho vazio!");
    }

    /**
     * Remove um item do carrinho.
     *
     * @param productId ID do produto a ser removido
     */
    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
        recalculateTotal();
        
        removeItemMessage();
    }

    /**
     * Atualiza a quantidade de um item no carrinho.
     *
     * @param productId ID do produto
     * @param newQuantity Nova quantidade
     */
    public void updateQuantity(Long productId, int newQuantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(newQuantity);
                break;
            }
        }
        recalculateTotal();
    }

    /**
     * Limpa todos os itens do carrinho.
     */
    public void clearCart() {
        cartItems.clear();
        total = BigDecimal.ZERO;
        removeAllItens();
    }

    /**
     * Recalcula o total do carrinho.
     */
    private void recalculateTotal() {
        total = cartItems.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Getters e Setters
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
