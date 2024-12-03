package com.github.jeanbezerra.ecommerce.web.portal.admin.helper;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class HibernateEntityManagerHelper implements Serializable {

	private static final long serialVersionUID = 8387588950122044879L;

	@Inject
	private static EntityManagerFactory emf;

	@Inject
	private static ThreadLocal<EntityManager> threadLocal;

	static {
		if (!isTestEnvironment()) {
			emf = Persistence.createEntityManagerFactory("web-unit");
			threadLocal = new ThreadLocal<>();
		}
	}

	private static boolean isTestEnvironment() {
		return "test".equals(System.getProperty("env"));
	}

	public static EntityManager getEntityManager() {
		EntityManager em = threadLocal.get();

		if (em == null || !em.isOpen()) {
			em = emf.createEntityManager();
			threadLocal.set(em);
			System.out.println(em.getDelegate());
		}
		return em;
	}

	public static void closeEntityManager() {
		EntityManager entityManager = getEntityManager();
		if (entityManager != null) {
			try {
				entityManager.close();
			} catch (Exception e) {
				throw new RuntimeException("Failed to close EntityManager", e);
			} finally {
				getThreadLocal().remove();
			}
		}
	}

	public static void closeEntityManagerFactory() {
		emf.close();
	}

	public static void beginTransaction() {
		EntityManager em = getEntityManager();
		if (em != null && !em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
	}

	public static void rollback() {
		EntityManager em = getEntityManager();
		if (em != null && em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
	}

	public static void commit() {
		EntityManager em = getEntityManager();
		if (em != null && em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
	}

	public static ThreadLocal<EntityManager> getThreadLocal() {
		return threadLocal;
	}

	public static void setThreadLocal(ThreadLocal<EntityManager> threadLocal) {
		HibernateEntityManagerHelper.threadLocal = threadLocal;
	}

}
