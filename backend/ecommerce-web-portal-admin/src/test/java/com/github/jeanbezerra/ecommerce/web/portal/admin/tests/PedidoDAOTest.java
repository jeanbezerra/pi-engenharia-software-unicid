package com.github.jeanbezerra.ecommerce.web.portal.admin.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.PedidoDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Cliente;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Pedido;
import com.github.jeanbezerra.ecommerce.web.portal.admin.helper.HibernateEntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class PedidoDAOTest {

	@BeforeAll
	static void setUp() {
		System.setProperty("env", "test");
	}

	@AfterEach
	void tearDown() {
		Mockito.clearAllCaches();
	}

	@Test
	void testFindAllPedidos() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			TypedQuery<Pedido> mockQuery = mock(TypedQuery.class);
			when(mockEntityManager.createQuery(anyString(), eq(Pedido.class))).thenReturn(mockQuery);

			List<Pedido> mockPedidos = List.of(
					new Pedido(1L, new Cliente(), LocalDateTime.now(), "EM_PROCESSAMENTO", null),
					new Pedido(2L, new Cliente(), LocalDateTime.now(), "FINALIZADO", null));

			when(mockQuery.getResultList()).thenReturn(mockPedidos);

			PedidoDAO pedidoDAO = new PedidoDAO();
			List<Pedido> result = pedidoDAO.findAllPedidos();

			assertNotNull(result);
			assertEquals(2, result.size());
			assertEquals("EM_PROCESSAMENTO", result.get(0).getStatus());
			assertEquals("FINALIZADO", result.get(1).getStatus());

			verify(mockEntityManager, times(1)).createQuery(anyString(), eq(Pedido.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

	@Test
	void testFindById() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Pedido mockPedido = new Pedido(1L, new Cliente(), LocalDateTime.now(), "EM_PROCESSAMENTO", null);
			when(mockEntityManager.find(Pedido.class, 1L)).thenReturn(mockPedido);

			PedidoDAO pedidoDAO = new PedidoDAO();
			Pedido result = pedidoDAO.findById(1L);

			assertNotNull(result);
			assertEquals(1L, result.getPedidoId());
			assertEquals("EM_PROCESSAMENTO", result.getStatus());

			verify(mockEntityManager, times(1)).find(Pedido.class, 1L);
		}
	}

	@Test
	void testSalvarPedido() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			// Mockando EntityTransaction
			EntityTransaction mockTransaction = mock(EntityTransaction.class);
			when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
			when(mockTransaction.isActive()).thenReturn(true);

			Pedido novoPedido = new Pedido(null, new Cliente(), LocalDateTime.now(), "EM_PROCESSAMENTO", null);

			PedidoDAO pedidoDAO = new PedidoDAO();
			pedidoDAO.salvarPedido(novoPedido);

			verify(mockEntityManager, times(1)).persist(novoPedido);
			verify(mockTransaction, times(1)).commit();
		}
	}

	@Test
	void testAtualizarPedido() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			// Mockando EntityTransaction
			EntityTransaction mockTransaction = mock(EntityTransaction.class);
			when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
			when(mockTransaction.isActive()).thenReturn(true);

			Pedido pedidoExistente = new Pedido(1L, new Cliente(), LocalDateTime.now(), "FINALIZADO", null);

			PedidoDAO pedidoDAO = new PedidoDAO();
			pedidoDAO.atualizarPedido(pedidoExistente);

			verify(mockEntityManager, times(1)).merge(pedidoExistente);
			verify(mockTransaction, times(1)).commit();
		}
	}

}
