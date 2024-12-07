package com.github.jeanbezerra.ecommerce.web.portal.security;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;

import com.github.jeanbezerra.ecommerce.web.portal.dao.ClienteDAO;
import com.github.jeanbezerra.ecommerce.web.portal.dao.UserDAO;
import com.github.jeanbezerra.ecommerce.web.portal.entity.Cliente;
import com.github.jeanbezerra.ecommerce.web.portal.entity.UserEntity;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class AuthenticationController implements Serializable {

	private static final long serialVersionUID = -1511756484896565774L;

	private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

	private static final String HOME_PAGE = "index.jsf?faces-redirect=true";
	private static final String REGISTER_PAGE = "register.jsf?faces-redirect=true";
	private static final String SUCCESSFULLY_REGISTERED_PAGE = "successfullyRegistered.jsf?faces-redirect=true";

	@Inject
	private Cliente cliente;

	@Inject
	private ClienteDAO clienteDAO;

	@Inject
	private UserEntity user;

	@Inject
	private UserDAO userDAO;

	private String fieldUsername;
	private String fieldPassword;
	private boolean fieldRememberMe;
	private Subject currentUser;
	private boolean loggedIn;

	public AuthenticationController() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadAll() {
		try {

			this.user = new UserEntity();
			this.fieldRememberMe = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loginUser() {

		try {

			this.user = new UserEntity();
			this.user = this.userDAO.readObject(this.fieldUsername);
			
			this.cliente = this.clienteDAO.findByEmail(this.fieldUsername);

			String tempUserParam = StringUtils.normalizeSpace(this.fieldUsername);
			String tempPassParam = StringUtils.normalizeSpace(this.fieldPassword);

			final UsernamePasswordToken token = new UsernamePasswordToken(tempUserParam, tempPassParam.toCharArray());
			this.currentUser = SecurityUtils.getSubject();
			this.currentUser.login(token);
			LOGGER.info("KB-SEC-0001 Authentication transaction processed successfully");

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			this.loggedIn = true;
			ec.redirect(HOME_PAGE);

		} catch (UnknownAccountException uae) {
			// uae.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Problema com seu usuário"));
			resetForm();
			this.loggedIn = false;
			LOGGER.error("KB-SEC-0002 Your username wrong");
		} catch (IncorrectCredentialsException ice) {
			// ice.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Senha incorreta"));
			resetForm();
			this.loggedIn = false;
			LOGGER.error("KB-SEC-0003 Password is incorrect");
		} catch (LockedAccountException lae) {
			// lae.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Conta bloqueada"));
			resetForm();
			this.loggedIn = false;
			LOGGER.error("KB-SEC-0004 This username is locked");
		} catch (AuthenticationException aex) {
			// aex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", aex.getMessage()));
			resetForm();
			this.loggedIn = false;
			LOGGER.error("KB-SEC-0005 Generic Exception Authentication");
		} catch (Exception e) {
			// e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			resetForm();
			this.loggedIn = false;
			LOGGER.error("KB-SEC-006 Generic Exception");
		}
	}

	public void authorizedUserControl() {
		if (null != SecurityUtils.getSubject().getPrincipal()) {
			final NavigationHandler nh = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
			nh.handleNavigation(FacesContext.getCurrentInstance(), null, HOME_PAGE);
		}
	}

	public String registerRedirect() {
		try {
			resetForm();
			this.user = new UserEntity();
			return REGISTER_PAGE;
		} catch (Exception e) {
			return HOME_PAGE;
		}
	}

	public String registerNewUser() {
		try {

			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			Object salt = rng.nextBytes();
			String hashedPasswordBase64 = new Sha256Hash(this.user.getPassword(), salt, 1024).toBase64();

			this.user.setPassword(hashedPasswordBase64);
			this.user.setPasswordSalt(salt.toString());
			// this.userDAO.persistObject(this.user);
			this.userDAO.persistObject(this.user);

			return SUCCESSFULLY_REGISTERED_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			return HOME_PAGE;
		}
	}

	public String resetForm() {
		this.fieldUsername = null;
		this.fieldPassword = null;
		return logout();
	}

	public String logout() {
		try {

			SecurityUtils.getSubject().logout();
//			DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
//			DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
//			Collection<Session> activeSessions = sessionManager.getSessionDAO().getActiveSessions();
//			for (Session session : activeSessions) {
//				if (currentUser.getSession().equals(session.getId())) {
//					session.stop();
//				}
//			}
			this.loggedIn = false;
			return "/authentication?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return HOME_PAGE;
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getFieldUsername() {
		return fieldUsername;
	}

	public void setFieldUsername(String fieldUsername) {
		this.fieldUsername = fieldUsername;
	}

	public String getFieldPassword() {
		return fieldPassword;
	}

	public void setFieldPassword(String fieldPassword) {
		this.fieldPassword = fieldPassword;
	}

	public boolean isFieldRememberMe() {
		return fieldRememberMe;
	}

	public void setFieldRememberMe(boolean fieldRememberMe) {
		this.fieldRememberMe = fieldRememberMe;
	}

	public Subject getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Subject currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	
}