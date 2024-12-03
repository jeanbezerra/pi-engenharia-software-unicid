package com.github.jeanbezerra.ecommerce.web.portal.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.jeanbezerra.ecommerce.web.portal.entity.Cliente;
import com.github.jeanbezerra.ecommerce.web.portal.helper.HibernateEntityManagerHelper;

import jakarta.ejb.Stateless;
import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Stateless
public class ClienteDAO implements Serializable {

	private static final long serialVersionUID = -8181768604396539106L;

	public ClienteDAO() {
		// TODO Auto-generated constructor stub
	}

	public void save(Cliente cliente) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			HibernateEntityManagerHelper.beginTransaction();
			if (cliente.getClienteId() == null) {
				entityManager.persist(cliente);
			} else {
				entityManager.merge(cliente);
			}
			HibernateEntityManagerHelper.commit();
		} catch (Exception e) {
			if (entityManager != null) {
				HibernateEntityManagerHelper.rollback();
			}
			e.printStackTrace();
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}

	public void delete(Long clienteId) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			HibernateEntityManagerHelper.beginTransaction();
			Cliente cliente = entityManager.find(Cliente.class, clienteId);
			if (cliente != null) {
				entityManager.remove(cliente);
			}
			HibernateEntityManagerHelper.commit();
		} catch (Exception e) {
			if (entityManager != null) {
				HibernateEntityManagerHelper.rollback();
			}
			e.printStackTrace();
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}

	public Cliente findById(Long clienteId) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			return entityManager.find(Cliente.class, clienteId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}
	
	public Cliente findByEmail(String email) {
	    EntityManager entityManager = null;
	    try {
	        entityManager = HibernateEntityManagerHelper.getEntityManager();
	        
	        // Usando native query
	        String sql = "SELECT * FROM clientes WHERE email = ?";
	        Object result = entityManager.createNativeQuery(sql, Cliente.class)
	                                     .setParameter(1, email)
	                                     .getSingleResult();

	        return (Cliente) result; // Converter o resultado para a entidade Cliente
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null; // Retorna null em caso de exceção
	    } finally {
	        if (entityManager != null) {
	            HibernateEntityManagerHelper.closeEntityManager();
	        }
	    }
	}

	public List<Cliente> findAll() {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}
	
	public List<SelectItem> findAllAsSelectItems() {
	    EntityManager entityManager = null;
	    List<SelectItem> options = new ArrayList<>();

	    try {
	        entityManager = HibernateEntityManagerHelper.getEntityManager();
	        TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class);
	        List<Cliente> clientes = query.getResultList();

	        for (Cliente cliente : clientes) {
	            options.add(new SelectItem(cliente.getClienteId(), cliente.getNome()));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (entityManager != null) {
	            HibernateEntityManagerHelper.closeEntityManager();
	        }
	    }

	    return options;
	}

}
