package com.github.jeanbezerra.ecommerce.web.portal.admin.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Categoria;
import com.github.jeanbezerra.ecommerce.web.portal.admin.helper.HibernateEntityManagerHelper;

import jakarta.ejb.Stateless;
import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Stateless
public class CategoriaDAO implements Serializable {

	private static final long serialVersionUID = 7516244381957717991L;

	public CategoriaDAO() {
		// TODO Auto-generated constructor stub
	}

	public void save(Categoria categoria) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			HibernateEntityManagerHelper.beginTransaction();
			if (categoria.getCategoriaId() == null) {
				entityManager.persist(categoria); // Inserir nova categoria
			} else {
				entityManager.merge(categoria); // Atualizar categoria existente
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

	public void delete(Long categoriaId) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			HibernateEntityManagerHelper.beginTransaction();
			Categoria categoria = entityManager.find(Categoria.class, categoriaId);
			if (categoria != null) {
				entityManager.remove(categoria);
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

	public Categoria findById(Long categoriaId) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			return entityManager.find(Categoria.class, categoriaId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}

	public List<Categoria> findAll() {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			TypedQuery<Categoria> query = entityManager.createQuery("SELECT c FROM Categoria c", Categoria.class);
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
	        TypedQuery<Categoria> query = entityManager.createQuery("SELECT c FROM Categoria c", Categoria.class);
	        List<Categoria> categorias = query.getResultList();

	        for (Categoria categoria : categorias) {
	            // Adiciona cada Categoria como SelectItem (value e label)
	            options.add(new SelectItem(categoria.getCategoriaId(), categoria.getNome()));
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
