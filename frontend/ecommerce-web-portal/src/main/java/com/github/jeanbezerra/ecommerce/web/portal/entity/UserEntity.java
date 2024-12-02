package com.github.jeanbezerra.ecommerce.web.portal.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "users", schema = "public")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 3774242315420933734L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", unique = true, nullable = false, length = 50)
	private String id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "password_salt")
	private String passwordSalt;

	@Column(name = "name")
	private String name;

	@Transient
	@Column(name = "remember_me")
	private boolean rememberMe;

	public UserEntity() {
		// TODO Auto-generated constructor stub
	}

	public UserEntity(String id, String username, String password, String passwordSalt, String name,
			boolean rememberMe) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.passwordSalt = passwordSalt;
		this.name = name;
		this.rememberMe = rememberMe;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username + ", password=" + password + ", passwordSalt="
				+ passwordSalt + ", name=" + name + ", rememberMe=" + rememberMe + "]";
	}

}