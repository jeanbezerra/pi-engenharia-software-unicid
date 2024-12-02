package com.github.jeanbezerra.ecommerce.web.portal.admin.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Produto;
import com.github.jeanbezerra.ecommerce.web.portal.admin.helper.HibernateEntityManagerHelper;

import jakarta.ejb.Stateless;
import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Stateless
public class ProdutoDAO implements Serializable {

	private static final long serialVersionUID = -5785915273592522080L;

	public ProdutoDAO() {
		// TODO Auto-generated constructor stub
	}

	public void save(Produto produto) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			HibernateEntityManagerHelper.beginTransaction();
			if (produto.getProdutoId() == null) {
				entityManager.persist(produto); // Insere novo produto
			} else {
				entityManager.merge(produto); // Atualiza produto existente
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

	public void delete(Long produtoId) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			HibernateEntityManagerHelper.beginTransaction();
			Produto produto = entityManager.find(Produto.class, produtoId);
			if (produto != null) {
				entityManager.remove(produto);
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

	public Produto findById(Long produtoId) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			return entityManager.find(Produto.class, produtoId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}

	public List<Produto> findAll() {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			TypedQuery<Produto> query = entityManager.createQuery("SELECT p FROM Produto p", Produto.class);
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
	        TypedQuery<Produto> query = entityManager.createQuery("SELECT p FROM Produto p", Produto.class);
	        List<Produto> produtos = query.getResultList();

	        for (Produto produto : produtos) {
	            // Adiciona cada Categoria como SelectItem (value e label)
	            options.add(new SelectItem(produto.getProdutoId(), produto.getNome()));
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