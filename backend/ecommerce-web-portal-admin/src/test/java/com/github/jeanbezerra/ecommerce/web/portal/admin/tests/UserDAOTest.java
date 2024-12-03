package com.github.jeanbezerra.ecommerce.web.portal.admin.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.UserDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.UserEntity;
import com.github.jeanbezerra.ecommerce.web.portal.admin.helper.HibernateEntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class UserDAOTest {

	@BeforeAll
	static void setUp() {
		System.setProperty("env", "test");
	}

	@AfterEach
	void tearDown() {
		Mockito.clearAllCaches();
	}

	@Test
	void testPersistObject() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);

			// Configuração e uso do mock estático
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);
			mockedHelper.when(HibernateEntityManagerHelper::beginTransaction).thenAnswer(invocation -> null);
			mockedHelper.when(HibernateEntityManagerHelper::commit).thenAnswer(invocation -> null);

			UserDAO userDAO = new UserDAO();
			UserEntity newUser = new UserEntity(null, "john.doe", "password123", "salt123", "John Doe", false, "email@email");

			userDAO.persistObject(newUser);

			verify(mockEntityManager, times(1)).merge(newUser);
			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
		}
	}

	@Test
	void testUpdateObject() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			// Configuração e uso do mock estático
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);
			mockedHelper.when(HibernateEntityManagerHelper::beginTransaction).thenAnswer(invocation -> null);
			mockedHelper.when(HibernateEntityManagerHelper::commit).thenAnswer(invocation -> null);

			UserDAO userDAO = new UserDAO();
			UserEntity existingUser = new UserEntity("1234", "john.doe", "password123", "salt123", "John Doe", false,"email@email");

			userDAO.updateObject(existingUser);

			verify(mockEntityManager, times(1)).merge(existingUser);
			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
		}
	}

	@Test
	void testRemoveObject() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			// Configuração e uso do mock estático
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);
			mockedHelper.when(HibernateEntityManagerHelper::beginTransaction).thenAnswer(invocation -> null);
			mockedHelper.when(HibernateEntityManagerHelper::commit).thenAnswer(invocation -> null);

			UserDAO userDAO = new UserDAO();
			UserEntity userToRemove = new UserEntity("1234", "john.doe", "password123", "salt123", "John Doe", false,"email@email");

			when(mockEntityManager.find(UserEntity.class, "1234")).thenReturn(userToRemove);

			userDAO.removeObject("1234");

			verify(mockEntityManager, times(1)).find(UserEntity.class, "1234");
			verify(mockEntityManager, times(1)).remove(userToRemove);
			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
		}
	}

	@Test
	void testReadObject() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Query mockQuery = mock(Query.class);
			when(mockEntityManager.createNativeQuery(anyString(), eq(UserEntity.class))).thenReturn(mockQuery);
			UserEntity mockUser = new UserEntity("1234", "john.doe", "password123", "salt123", "John Doe", false,"email@email");
			when(mockQuery.setParameter(eq("username"), anyString())).thenReturn(mockQuery);
			when(mockQuery.getSingleResult()).thenReturn(mockUser);

			UserDAO userDAO = new UserDAO();
			UserEntity result = userDAO.readObject("john.doe");

			assertNotNull(result);
			assertEquals("john.doe", result.getUsername());
			verify(mockEntityManager, times(1)).createNativeQuery(anyString(), eq(UserEntity.class));
			verify(mockQuery, times(1)).setParameter(eq("username"), eq("john.doe"));
			verify(mockQuery, times(1)).getSingleResult();
		}
	}

	@Test
	void testReadObject_NoResult() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Query mockQuery = mock(Query.class);
			when(mockEntityManager.createNativeQuery(anyString(), eq(UserEntity.class))).thenReturn(mockQuery);
			when(mockQuery.setParameter(eq("username"), anyString())).thenReturn(mockQuery);
			when(mockQuery.getSingleResult()).thenThrow(NoResultException.class);

			UserDAO userDAO = new UserDAO();
			UserEntity result = userDAO.readObject("nonexistent.user");

			assertNull(result); // Verifica que o retorno é null
			verify(mockEntityManager, times(1)).createNativeQuery(anyString(), eq(UserEntity.class));
		}
	}

	@Test
	void testReadObjectById_UserExists() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			UserEntity mockUser = new UserEntity("1234", "john.doe", "password123", "salt123", "John Doe", false,"email@email");
			Query mockQuery = mock(Query.class);

			when(mockEntityManager.createNativeQuery(anyString(), eq(UserEntity.class))).thenReturn(mockQuery);
			when(mockQuery.setParameter(eq("id"), anyString())).thenReturn(mockQuery);
			when(mockQuery.getSingleResult()).thenReturn(mockUser);

			UserDAO userDAO = new UserDAO();
			UserEntity result = userDAO.readObjectById("1234");

			assertNotNull(result);
			assertEquals("1234", result.getId());
			verify(mockEntityManager, times(1)).createNativeQuery(anyString(), eq(UserEntity.class));
		}
	}

	@Test
	void testReadObjectById_UserNotFound() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Query mockQuery = mock(Query.class);
			when(mockEntityManager.createNativeQuery(anyString(), eq(UserEntity.class))).thenReturn(mockQuery);
			when(mockQuery.setParameter(eq("id"), anyString())).thenReturn(mockQuery);
			when(mockQuery.getSingleResult()).thenThrow(NoResultException.class);

			UserDAO userDAO = new UserDAO();
			UserEntity result = userDAO.readObjectById("9999");

			assertNull(result);
			verify(mockEntityManager, times(1)).createNativeQuery(anyString(), eq(UserEntity.class));
		}
	}
	
	@Test
	void testListAll_QueryError() {
	    try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
	        EntityManager mockEntityManager = mock(EntityManager.class);
	        mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

	        Query mockQuery = mock(Query.class);
	        when(mockEntityManager.createNativeQuery(anyString(), eq(UserEntity.class))).thenReturn(mockQuery);
	        when(mockQuery.getResultList()).thenThrow(RuntimeException.class);

	        UserDAO userDAO = new UserDAO();
	        List<UserEntity> result = userDAO.listAll();

	        assertNotNull(result);
	        assertTrue(result.isEmpty());
	        verify(mockEntityManager, times(1)).createNativeQuery(anyString(), eq(UserEntity.class));
	    }
	}


	@Test
	void testListAll() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			// Mock do Query
			Query mockQuery = mock(Query.class);
			when(mockEntityManager.createNativeQuery(anyString(), eq(UserEntity.class))).thenReturn(mockQuery);

			// Lista simulada de resultados
			List<UserEntity> mockUsers = new ArrayList<>();
			mockUsers.add(new UserEntity("1234", "john.doe", "password123", "salt123", "John Doe", false,"email@email"));
			mockUsers.add(new UserEntity("5678", "jane.doe", "password456", "salt456", "Jane Doe", true,"email@email"));

			when(mockQuery.getResultList()).thenReturn(mockUsers);

			UserDAO userDAO = new UserDAO();
			List<UserEntity> result = userDAO.listAll();

			// Verificações
			assertNotNull(result);
			assertEquals(2, result.size());
			assertEquals("john.doe", result.get(0).getUsername());
			assertEquals("jane.doe", result.get(1).getUsername());

			// Verifique se os métodos foram chamados corretamente
			verify(mockEntityManager, times(1)).createNativeQuery(anyString(), eq(UserEntity.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

}
