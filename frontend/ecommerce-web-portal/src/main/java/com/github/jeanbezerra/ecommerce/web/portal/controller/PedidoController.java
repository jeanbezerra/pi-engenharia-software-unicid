package com.github.jeanbezerra.ecommerce.web.portal.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.jeanbezerra.ecommerce.web.portal.dao.ClienteDAO;
import com.github.jeanbezerra.ecommerce.web.portal.dao.PedidoDAO;
import com.github.jeanbezerra.ecommerce.web.portal.dao.PedidoItemDAO;
import com.github.jeanbezerra.ecommerce.web.portal.dao.ProdutoDAO;
import com.github.jeanbezerra.ecommerce.web.portal.entity.CartItem;
import com.github.jeanbezerra.ecommerce.web.portal.entity.ItemPedido;
import com.github.jeanbezerra.ecommerce.web.portal.entity.Pedido;
import com.github.jeanbezerra.ecommerce.web.portal.entity.Produto;
import com.github.jeanbezerra.ecommerce.web.portal.security.AuthenticationController;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@SessionScoped
public class PedidoController implements Serializable {

	private static final long serialVersionUID = 4960393959300889318L;
	
	@Inject
	private AuthenticationController authenticationController;

	@Inject
	private ClienteDAO clienteDAO = new ClienteDAO();

	@Inject
	private ProdutoDAO produtoDAO = new ProdutoDAO();

	@Inject
	private PedidoDAO pedidoDAO = new PedidoDAO();

	@Inject
	private PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
	
	@Inject
	private CartController cartController;

	private Pedido pedidoSelecionado = new Pedido();
	private Pedido novoPedido = new Pedido();
	private ItemPedido novoItem = new ItemPedido();

	private List<Pedido> pedidos = new ArrayList<>();
	private List<ItemPedido> itensDataTable = new ArrayList<>();
	private List<SelectItem> clientesOptions = new ArrayList<>();
	private List<SelectItem> produtosOptions = new ArrayList<>();
	private List<Pedido> meusPedidos = new ArrayList<>();

	private Long pedidoID = 0L;
	private Long clienteID = 0L;
	private Long produtoID = 0L;
	private Integer produtoQTD;
	
	private String orderNumber;

	@PostConstruct
	public void init() {
		reloadAll();
	}

	public double calcularValorTotal(Pedido pedido) {
		return pedido.getItems() == null || pedido.getItems().isEmpty() ? 0.0
				: pedido.getItems().stream().mapToDouble(i -> i.getPrecoUnitario().doubleValue() * i.getQuantidade())
						.sum();
	}

	public String gerarNovoPedido() {
		try {
			reloadAll();
			novoPedido = new Pedido();
			novoPedido.setStatus("Novo");
			novoPedido.setDataPedido(LocalDateTime.now());
			novoPedido.setCliente(clienteDAO.findById(1L));
			pedidoDAO.salvarPedido(novoPedido);

			pedidoSelecionado = novoPedido;
			pedidoID = novoPedido.getPedidoId();
			return "order-new.jsf?faces-redirect=true";
		} catch (Exception e) {
			handleException(e, "Erro ao criar novo pedido.");
			return null;
		}
	}
	
	public String gerarNovoPedidoFinalUser() {
		try {
			
			String email = this.authenticationController.getUser().getEmail();
			
			reloadAll();
			novoPedido = new Pedido();
			novoPedido.setStatus("Novo");
			novoPedido.setDataPedido(LocalDateTime.now());
			novoPedido.setCliente(this.clienteDAO.findByEmail(email));	
			
			pedidoDAO.salvarPedido(novoPedido);

			this.pedidoSelecionado = novoPedido;
			this.pedidoID = novoPedido.getPedidoId();
			
			
			List<ItemPedido> itens = new ArrayList<>();
			if(this.cartController.getCartItems().size()!=0) {
				
				
				
				for (CartItem item : this.cartController.getCartItems()) {					
					ItemPedido ip = new ItemPedido();					
					
					ip.setPedido(this.novoPedido);
					ip.setPrecoUnitario(item.getUnitPrice());
					ip.setQuantidade(item.getQuantity());
					ip.setProduto(this.produtoDAO.findById(item.getProduct().getId()));					
					itens.add(ip);					
				}
				
			}
			
			this.orderNumber = this.novoPedido.getPedidoId().toString();
			
			for (ItemPedido itemParaSalvar : itens) {
				pedidoItemDAO.salvarItemPedido(itemParaSalvar);
			}	

			
			reloadAll();
			this.cartController.clearCart();
			
			return "order-new.jsf?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return "cart.jsf?faces-redirect=true";
		}
	}

	public List<Pedido> filtrarPedidosPorStatus(String status) {
		return pedidos.stream().filter(p -> status.equals(p.getStatus())).collect(Collectors.toList());
	}

	public void finalizarPedido(Pedido pedido) {
		try {
			pedido.setStatus("Finalizado");
			pedidoDAO.atualizarPedido(pedido);
			pedidos = pedidoDAO.findAllPedidos();
			addMessage(FacesMessage.SEVERITY_INFO, "Pedido finalizado com sucesso!");
		} catch (Exception e) {
			handleException(e, "Erro ao finalizar o pedido.");
		}
	}

	@Transactional
	public void adicionarItem() {
		try {
			validarItem();
			Produto produtoTemp = produtoDAO.findById(produtoID);
			novoItem.setPedido(novoPedido);
			novoItem.setPrecoUnitario(produtoTemp.getPreco());
			novoItem.setProduto(produtoTemp);
			novoItem.setQuantidade(produtoQTD);

			pedidoItemDAO.salvarItemPedido(novoItem);

			itensDataTable = pedidoItemDAO.buscarItensPorPedidoId(novoPedido.getPedidoId());
			novoItem = new ItemPedido();
			addMessage(FacesMessage.SEVERITY_INFO, "Produto adicionado ao pedido!");
		} catch (Exception e) {
			handleException(e, "Erro ao adicionar o item.");
		}
	}

	@Transactional
	public String salvarPedido() {
		try {
			novoPedido = pedidoDAO.findById(pedidoID);
			novoPedido.setCliente(clienteDAO.findById(clienteID));
			novoPedido.setItems(pedidoItemDAO.buscarItensPorPedidoId(pedidoID));
			novoPedido.setDataPedido(LocalDateTime.now());
			novoPedido.setStatus("Aguardando Pagamento");

			pedidoDAO.atualizarPedido(novoPedido);
			addMessage(FacesMessage.SEVERITY_INFO, "Pedido salvo com sucesso!");
			return "orders-manager.jsf?faces-redirect=true";
		} catch (Exception e) {
			handleException(e, "Erro ao salvar o pedido.");
			return "order-new.jsf?faces-redirect=true";
		} finally {
			reloadAll();
		}
	}

	private void reloadAll() {
		clientesOptions = clienteDAO.findAllAsSelectItems();
		produtosOptions = produtoDAO.findAllAsSelectItems();
		pedidos = pedidoDAO.findAllPedidos();
		novoPedido = new Pedido();
		novoItem = new ItemPedido();
		pedidoSelecionado = new Pedido();
		itensDataTable.clear();
		this.meusPedidos = this.pedidoDAO.findPedidosByEmail(this.authenticationController.getCliente().getEmail());
	}

	private void validarItem() {
		if (produtoID == null || produtoQTD == null || produtoQTD <= 0) {
			throw new IllegalArgumentException("Produto e quantidade devem ser válidos.");
		}
	}

	private void addMessage(FacesMessage.Severity severity, String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
	}

	private void handleException(Exception e, String errorMessage) {
		addMessage(FacesMessage.SEVERITY_ERROR, errorMessage);
		e.printStackTrace();
	}

	public Pedido getPedidoSelecionado() {
		return pedidoSelecionado;
	}

	public void setPedidoSelecionado(Pedido pedidoSelecionado) {
		this.pedidoSelecionado = pedidoSelecionado;
	}

	public Pedido getNovoPedido() {
		return novoPedido;
	}

	public void setNovoPedido(Pedido novoPedido) {
		this.novoPedido = novoPedido;
	}

	public ItemPedido getNovoItem() {
		return novoItem;
	}

	public void setNovoItem(ItemPedido novoItem) {
		this.novoItem = novoItem;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<ItemPedido> getItensDataTable() {
		return itensDataTable;
	}

	public void setItensDataTable(List<ItemPedido> itensDataTable) {
		this.itensDataTable = itensDataTable;
	}

	public List<SelectItem> getClientesOptions() {
		return clientesOptions;
	}

	public void setClientesOptions(List<SelectItem> clientesOptions) {
		this.clientesOptions = clientesOptions;
	}

	public List<SelectItem> getProdutosOptions() {
		return produtosOptions;
	}

	public void setProdutosOptions(List<SelectItem> produtosOptions) {
		this.produtosOptions = produtosOptions;
	}

	public Long getPedidoID() {
		return pedidoID;
	}

	public void setPedidoID(Long pedidoID) {
		this.pedidoID = pedidoID;
	}

	public Long getClienteID() {
		return clienteID;
	}

	public void setClienteID(Long clienteID) {
		this.clienteID = clienteID;
	}

	public Long getProdutoID() {
		return produtoID;
	}

	public void setProdutoID(Long produtoID) {
		this.produtoID = produtoID;
	}

	public Integer getProdutoQTD() {
		return produtoQTD;
	}

	public void setProdutoQTD(Integer produtoQTD) {
		this.produtoQTD = produtoQTD;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<Pedido> getMeusPedidos() {
		return meusPedidos;
	}

	public void setMeusPedidos(List<Pedido> meusPedidos) {
		this.meusPedidos = meusPedidos;
	}

	
	
}
