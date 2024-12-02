package com.github.jeanbezerra.ecommerce.web.portal.admin.controller;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.ClienteDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Cliente;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ClienteController implements Serializable {

	private static final Logger LOGGER = LogManager.getLogger(ClienteController.class);
	private static final long serialVersionUID = 6017427583336686491L;

	private ClienteDAO clienteDAO = new ClienteDAO();
	private Cliente selectedCliente = new Cliente();
	private List<Cliente> clientes;
	private boolean editMode = false;

	public ClienteController() {

	}

	@PostConstruct
	public void init() {
		loadClientes();
	}

	public void saveCliente() {
		try {
			
	        if (selectedCliente.getPassword() != null && !selectedCliente.getPassword().isEmpty()) {
	            RandomNumberGenerator rng = new SecureRandomNumberGenerator();
	            Object salt = rng.nextBytes();
	            String hashedPasswordBase64 = new Sha256Hash(selectedCliente.getPassword(), salt, 1024).toBase64();
	            selectedCliente.setPassword(hashedPasswordBase64);
	            selectedCliente.setPasswordSalt(salt.toString());
	        }
	        
	        if (editMode) {
	            clienteDAO.save(selectedCliente);
	        } else {
	            clienteDAO.save(selectedCliente);
	        }        
			
			loadClientes(); // Recarrega a lista de clientes
			resetForm(); // Reseta o formulário
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteCliente(Long clienteId) {
		try {
			clienteDAO.delete(clienteId);
			loadClientes(); // Recarrega a lista de clientes
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reloadAll() {
		try {
			this.clientes = this.clienteDAO.findAll();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareEdit(Cliente cliente) {
		this.selectedCliente = clienteDAO.findById(cliente.getClienteId());
		this.editMode = true;
	}

	private void loadClientes() {
		this.clientes = clienteDAO.findAll();
	}

	private void resetForm() {
		this.selectedCliente = new Cliente();
		this.editMode = false;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public Cliente getSelectedCliente() {
		return selectedCliente;
	}

	public void setSelectedCliente(Cliente selectedCliente) {
		this.selectedCliente = selectedCliente;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

}
