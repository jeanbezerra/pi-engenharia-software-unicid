package com.github.jeanbezerra.ecommerce.web.portal.util;

import com.github.jeanbezerra.ecommerce.web.portal.helper.HibernateEntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseInstanceListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {

			EntityManager entityManager = HibernateEntityManagerHelper.getEntityManager();
			String transactionQuery = "select 1 + 1";

			try {

				entityManager.createNativeQuery(transactionQuery).getSingleResult();

			} catch (Exception e) {
				e.printStackTrace();
				HibernateEntityManagerHelper.rollback();
			} finally {
				HibernateEntityManagerHelper.closeEntityManager();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Nothing
	}

}