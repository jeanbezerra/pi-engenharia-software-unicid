package com.github.jeanbezerra.ecommerce.web.portal.admin.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.github.jeanbezerra.ecommerce.web.portal.admin.helper.HibernateEntityManagerHelper;

import jakarta.persistence.EntityManager;

public class HibernateEntityManagerHelperTest {

	@BeforeAll
	static void setUp() {
		System.setProperty("env", "test");
	}

	@AfterEach
	void tearDown() {
		Mockito.clearAllCaches();
	}

//	@Test
//	void testGetEntityManager_Success() {
//		ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();
//		EntityManager mockEntityManager = mock(EntityManager.class);
//		threadLocal.set(mockEntityManager);
//
//		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
//			mockedHelper.when(HibernateEntityManagerHelper::getThreadLocal).thenReturn(threadLocal);
//
//			EntityManager result = HibernateEntityManagerHelper.getEntityManager();
//
//			assertNotNull(result);
//			assertEquals(mockEntityManager, result);
//		}
//	}
//
//	@Test
//	void testCloseEntityManager_Success() {
//		ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();
//		EntityManager mockEntityManager = mock(EntityManager.class);
//		threadLocal.set(mockEntityManager);
//
//		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
//			mockedHelper.when(HibernateEntityManagerHelper::getThreadLocal).thenReturn(threadLocal);
//
//			HibernateEntityManagerHelper.closeEntityManager();
//
//			verify(mockEntityManager, times(1)).close();
//			assertNull(threadLocal.get()); // Verifica que o ThreadLocal foi limpo
//		}
//	}
//
//	@Test
//	void testCloseEntityManager_ThrowsException() {
//		ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();
//		EntityManager mockEntityManager = mock(EntityManager.class);
//		threadLocal.set(mockEntityManager);
//
//		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
//			mockedHelper.when(HibernateEntityManagerHelper::getThreadLocal).thenReturn(threadLocal);
//			doThrow(new RuntimeException("Failed to close")).when(mockEntityManager).close();
//
//			RuntimeException exception = assertThrows(RuntimeException.class,
//					HibernateEntityManagerHelper::closeEntityManager);
//
//			assertEquals("Failed to close", exception.getMessage());
//		}
//	}

}
