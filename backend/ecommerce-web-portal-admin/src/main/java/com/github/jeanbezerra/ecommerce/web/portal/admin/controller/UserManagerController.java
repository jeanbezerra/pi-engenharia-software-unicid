package com.github.jeanbezerra.ecommerce.web.portal.admin.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.UserDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.UserEntity;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UserManagerController implements Serializable {

	private static final Logger LOGGER = LogManager.getLogger(UserManagerController.class);
	private static final long serialVersionUID = 6168530346948146257L;
	private List<UserEntity> users = new ArrayList<>();
	private UserEntity selectedUser = new UserEntity();
	private boolean editMode = false;

	@Inject
	private UserDAO userDAO = new UserDAO();

	// Simula uma lista de usuários (substitua por uma chamada ao service no futuro)
	public UserManagerController() {

	}

	@PostConstruct
	public void init() {
		this.users = this.userDAO.listAll();
	}

	// Método para salvar ou atualizar um usuário
	public void saveUser() {
	    try {
	        // Verifica se uma nova senha foi fornecida
	        if (selectedUser.getPassword() != null && !selectedUser.getPassword().isEmpty()) {
	            RandomNumberGenerator rng = new SecureRandomNumberGenerator();
	            Object salt = rng.nextBytes(); // Gera um novo salt

	            // Criptografa a nova senha com o salt
	            String hashedPasswordBase64 = new Sha256Hash(selectedUser.getPassword(), salt, 1024).toBase64();

	            // Atualiza a senha e o salt do usuário
	            selectedUser.setPassword(hashedPasswordBase64);
	            selectedUser.setPasswordSalt(salt.toString());
	        }

	        // Salva ou atualiza o usuário
	        if (editMode) {
	            userDAO.updateObject(selectedUser);
	        } else {
	            userDAO.persistObject(selectedUser);
	        }

	        loadUsers(); // Recarrega a lista de usuários
	        resetForm(); // Reseta o formulário
	    } catch (Exception e) {
	    	e.printStackTrace();
	        //LOGGER.error("Error while saving user: {}", e.getMessage());
	    }
	}


	// Método para excluir um usuário
	public void deleteUser(String id) {
		try {
			userDAO.removeObject(id); // Remove o usuário pelo ID
			loadUsers(); // Recarrega a lista de usuários
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Prepara o formulário para edição de um usuário
	// Prepara o formulário para edição de um usuário
	public void prepareEdit(UserEntity user) {
	    try {
	        // Busca o usuário do banco de dados pelo ID para garantir que o ORM gerencie o objeto
	        this.selectedUser = userDAO.readObjectById(user.getId());
	        if (this.selectedUser == null) {
	        	System.out.println("User with ID {} not found " + user.getId());
	            //LOGGER.warn("User with ID {} not found", user.getId());
	        } else {
	            this.editMode = true;
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        //LOGGER.error("Error while preparing user for edit: {}", e.getMessage());
	    }
	}


	// Recarrega a lista de usuários do banco
	private void loadUsers() {
		try {
			this.users = new ArrayList<UserEntity>();
			this.users = userDAO.listAll(); // Busca todos os usuários do banco
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshData() {
		loadUsers();
	}

	// Reseta o formulário e as variáveis
	private void resetForm() {
		this.selectedUser = new UserEntity();
		this.editMode = false;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public UserEntity getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserEntity selectedUser) {
		this.selectedUser = selectedUser;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
