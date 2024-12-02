package com.github.jeanbezerra.ecommerce.web.portal.dao;

import java.io.Serializable;
import java.util.List;

import com.github.jeanbezerra.ecommerce.web.portal.entity.ItemPedido;
import com.github.jeanbezerra.ecommerce.web.portal.entity.Pedido;
import com.github.jeanbezerra.ecommerce.web.portal.helper.HibernateEntityManagerHelper;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Stateless
public class PedidoDAO implements Serializable {

	private static final long serialVersionUID = -2829359106455696163L;

	public PedidoDAO() {
		// TODO Auto-generated constructor stub
	}

	public List<Pedido> findAllPedidos() {
		EntityManager entityManager = null;

		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();

			// Carregar pedidos com cliente e itens
			TypedQuery<Pedido> query = entityManager.createQuery(
					"SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.items i LEFT JOIN FETCH i.produto LEFT JOIN FETCH p.cliente ",
					Pedido.class);

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
	
	public void atualizarPedido(Pedido pedido) {
	    EntityManager entityManager = null;

	    try {
	        entityManager = HibernateEntityManagerHelper.getEntityManager();
	        entityManager.getTransaction().begin();
	        entityManager.merge(pedido); // Atualiza o pedido no banco
	        entityManager.getTransaction().commit();
	    } catch (Exception e) {
	        if (entityManager != null && entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().rollback();
	        }
	        e.printStackTrace();
	        throw e; // Repropaga a exceção
	    } finally {
	        if (entityManager != null) {
	            HibernateEntityManagerHelper.closeEntityManager();
	        }
	    }
	}
	
	public void salvarItemPedido(ItemPedido itemPedido) {
	    EntityManager entityManager = null;

	    try {
	        entityManager = HibernateEntityManagerHelper.getEntityManager();
	        entityManager.getTransaction().begin();
	        entityManager.persist(itemPedido); // Persiste o item do pedido
	        entityManager.getTransaction().commit();
	    } catch (Exception e) {
	        if (entityManager != null && entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().rollback();
	        }
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (entityManager != null) {
	            HibernateEntityManagerHelper.closeEntityManager();
	        }
	    }
	}


	
	public void salvarPedido(Pedido pedido) {
	    EntityManager entityManager = null;

	    try {
	        entityManager = HibernateEntityManagerHelper.getEntityManager();
	        entityManager.getTransaction().begin();
	        entityManager.persist(pedido); // Salva o pedido
	        entityManager.getTransaction().commit();
	    } catch (Exception e) {
	        if (entityManager != null && entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().rollback();
	        }
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (entityManager != null) {
	            HibernateEntityManagerHelper.closeEntityManager();
	        }
	    }
	}
	
	public Pedido findById(Long pedidoId) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			return entityManager.find(Pedido.class, pedidoId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}
}
