package com.github.jeanbezerra.ecommerce.web.portal.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.jeanbezerra.ecommerce.web.portal.entity.UserEntity;
import com.github.jeanbezerra.ecommerce.web.portal.helper.HibernateEntityManagerHelper;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

@Stateless
public class UserDAO implements GenericDAO<UserEntity>, Serializable {

	private static final long serialVersionUID = 3027144437385845342L;
	private static final Logger LOGGER = LogManager.getLogger(UserDAO.class);

	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void persistObject(UserEntity entity) {
		try {
			EntityManager entityManager = HibernateEntityManagerHelper.getEntityManager();

			try {

				HibernateEntityManagerHelper.beginTransaction();
				entityManager.persist(entity);
				HibernateEntityManagerHelper.commit();
				LOGGER.debug("new user successfully registered");

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
	public void updateObject(UserEntity entity) {
	    try {
	        EntityManager entityManager = HibernateEntityManagerHelper.getEntityManager();

	        try {
	            HibernateEntityManagerHelper.beginTransaction();
	            entityManager.merge(entity); // Atualizar o usuário existente
	            HibernateEntityManagerHelper.commit();
	            LOGGER.debug("User successfully updated");
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
	public void removeObject(String id) {
	    try {
	        EntityManager entityManager = HibernateEntityManagerHelper.getEntityManager();

	        try {
	            HibernateEntityManagerHelper.beginTransaction();
	            UserEntity user = entityManager.find(UserEntity.class, id);
	            if (user != null) {
	                entityManager.remove(user); // Remover usuário
	            }
	            HibernateEntityManagerHelper.commit();
	            LOGGER.debug("User successfully removed");
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

	/**
	 * @param String email
	 */
	@Override
	public UserEntity readObject(String username) {
		try {

			EntityManager entityManager = HibernateEntityManagerHelper.getEntityManager();
			String transactionQuery = "SELECT * FROM USERS WHERE username = :username ";
			UserEntity userEntity = new UserEntity();

			try {

				userEntity = (UserEntity) entityManager.createNativeQuery(transactionQuery, UserEntity.class).setParameter("username", username).getSingleResult();

			} catch (NoResultException nre) {
				LOGGER.debug("User not found {}", nre.getMessage());
			} catch (Exception e) {
				LOGGER.debug("Generic processing error {}", e.getMessage());
				HibernateEntityManagerHelper.rollback();
			} finally {
				HibernateEntityManagerHelper.closeEntityManager();
			}

			return userEntity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public UserEntity readObjectById(String id) {
	    EntityManager entityManager = null;
	    UserEntity userEntity = null;

	    try {
	        // Obter o EntityManager
	        entityManager = HibernateEntityManagerHelper.getEntityManager();

	        // Consulta para buscar o usuário pelo ID
	        String transactionQuery = "SELECT * FROM users WHERE id = :id";

	        // Executar a consulta e obter o resultado
	        try {
	            userEntity = (UserEntity) entityManager.createNativeQuery(transactionQuery, UserEntity.class)
	                                                   .setParameter("id", id)
	                                                   .getSingleResult();
	        } catch (NoResultException e) {
	            LOGGER.debug("User not found with ID: {}", id);
	        } catch (Exception e) {
	            LOGGER.error("Error executing query: {}", e.getMessage());
	        }
	    } catch (Exception e) {
	        LOGGER.error("Error initializing EntityManager: {}", e.getMessage());
	    } finally {
	        // Fechar o EntityManager no final
	        if (entityManager != null) {
	            HibernateEntityManagerHelper.closeEntityManager();
	        }
	    }

	    return userEntity;
	}


	@Override
	public List<UserEntity> listAll() {
		List<UserEntity> userEntities = new ArrayList<>();

	    try {
	        EntityManager entityManager = HibernateEntityManagerHelper.getEntityManager();
	        String transactionQuery = "SELECT * FROM users";

	        try {
	            // Executar a consulta para obter uma lista de usuários
	            userEntities = entityManager.createNativeQuery(transactionQuery, UserEntity.class).getResultList();
	        } catch (Exception e) {
	            LOGGER.debug("Error while fetching users: {}", e.getMessage());
	        } finally {
	            HibernateEntityManagerHelper.closeEntityManager();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return userEntities;
	}

	@Override
	public void removeObject(UserEntity t) {
		// TODO Auto-generated method stub

	}

}