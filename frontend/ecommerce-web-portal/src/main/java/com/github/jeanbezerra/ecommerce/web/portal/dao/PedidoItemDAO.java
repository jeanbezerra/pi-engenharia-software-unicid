package com.github.jeanbezerra.ecommerce.web.portal.dao;

import java.io.Serializable;
import java.util.List;

import com.github.jeanbezerra.ecommerce.web.portal.entity.ItemPedido;
import com.github.jeanbezerra.ecommerce.web.portal.helper.HibernateEntityManagerHelper;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Stateless
public class PedidoItemDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6198315056935786434L;

	/**
	 * Salvar um item do pedido no banco de dados.
	 */
	public void salvarItemPedido(ItemPedido itemPedido) {
		EntityManager entityManager = null;

		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(itemPedido);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar o item do pedido.", e);
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}

	/**
	 * Atualizar um item do pedido no banco de dados.
	 */
	public void atualizarItemPedido(ItemPedido itemPedido) {
		EntityManager entityManager = null;

		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			entityManager.getTransaction().begin();
			entityManager.merge(itemPedido);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar o item do pedido.", e);
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}

	/**
	 * Deletar um item do pedido no banco de dados.
	 */
	public void deletarItemPedido(ItemPedido itemPedido) {
		EntityManager entityManager = null;

		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			entityManager.getTransaction().begin();
			entityManager.remove(entityManager.contains(itemPedido) ? itemPedido : entityManager.merge(itemPedido));
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Erro ao deletar o item do pedido.", e);
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}

	/**
	 * Buscar todos os itens do pedido no banco de dados.
	 */
	public List<ItemPedido> buscarTodosItens() {
		EntityManager entityManager = null;

		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			TypedQuery<ItemPedido> query = entityManager.createQuery("SELECT i FROM ItemPedido i", ItemPedido.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar todos os itens do pedido.", e);
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}

	/**
	 * Buscar itens de um pedido específico.
	 */
	public List<ItemPedido> buscarItensPorPedidoId(Long pedidoId) {
		EntityManager entityManager = null;

		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			TypedQuery<ItemPedido> query = entityManager
					.createQuery("SELECT i FROM ItemPedido i WHERE i.pedido.id = :pedidoId", ItemPedido.class);
			query.setParameter("pedidoId", pedidoId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar itens do pedido por ID.", e);
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}

	/**
	 * Buscar um item específico por ID.
	 */
	public ItemPedido buscarItemPorId(Long itemId) {
		EntityManager entityManager = null;

		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			return entityManager.find(ItemPedido.class, itemId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar item do pedido por ID.", e);
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}
}
