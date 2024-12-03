package com.github.jeanbezerra.ecommerce.web.portal.admin.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.PedidoItemDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.ItemPedido;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Pedido;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Produto;
import com.github.jeanbezerra.ecommerce.web.portal.admin.helper.HibernateEntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class PedidoItemDAOTest {

	@BeforeAll
	static void setUp() {
		System.setProperty("env", "test");
	}

	@AfterEach
	void tearDown() {
		Mockito.clearAllCaches();
	}

	@Test
	void testSalvarItemPedido() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			// Mockando EntityTransaction
			EntityTransaction mockTransaction = mock(EntityTransaction.class);
			when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
			when(mockTransaction.isActive()).thenReturn(true);

			ItemPedido itemPedido = new ItemPedido(null, new Pedido(), new Produto(), 5, new BigDecimal("500.00"));

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			pedidoItemDAO.salvarItemPedido(itemPedido);

			verify(mockEntityManager, times(1)).persist(itemPedido);
			verify(mockTransaction, times(1)).commit();
		}
	}

	@Test
	void testAtualizarItemPedido() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			// Mockando EntityTransaction
			EntityTransaction mockTransaction = mock(EntityTransaction.class);
			when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
			when(mockTransaction.isActive()).thenReturn(true);

			ItemPedido itemPedido = new ItemPedido(1L, new Pedido(), new Produto(), 5, new BigDecimal("500.00"));

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			pedidoItemDAO.atualizarItemPedido(itemPedido);

			verify(mockEntityManager, times(1)).merge(itemPedido);
			verify(mockTransaction, times(1)).commit();
		}
	}

	@Test
	void testAtualizarItemPedido_TransactionFailure() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			// Mockando EntityTransaction
			EntityTransaction mockTransaction = mock(EntityTransaction.class);
			when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
			doThrow(new RuntimeException("Transaction failed")).when(mockTransaction).commit();

			ItemPedido itemPedido = new ItemPedido(1L, new Pedido(), new Produto(), 5, new BigDecimal("500.00"));

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();

			// Verificando se a exceção é lançada
			RuntimeException exception = assertThrows(RuntimeException.class,
					() -> pedidoItemDAO.atualizarItemPedido(itemPedido));

			// Validando a mensagem da exceção
			assertEquals("Transaction failed", exception.getCause().getMessage());

			// Verificando chamadas
			verify(mockEntityManager, times(1)).merge(itemPedido);
			verify(mockTransaction, times(1)).commit();
			verify(mockTransaction, never()).rollback(); // A lógica não está configurada para rollback nesse teste
		}
	}

	@Test
	void testSalvarItemPedido_TransactionFailure() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			// Mockando EntityTransaction
			EntityTransaction mockTransaction = mock(EntityTransaction.class);
			when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
			doThrow(new RuntimeException("Transaction failed")).when(mockTransaction).commit();
			when(mockTransaction.isActive()).thenReturn(true);

			ItemPedido itemPedido = new ItemPedido(null, new Pedido(), new Produto(), 5, new BigDecimal("500.00"));

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();

			// Trata a exceção capturada
			assertThrows(RuntimeException.class, () -> pedidoItemDAO.salvarItemPedido(itemPedido));
		}
	}

	@Test
	void testDeletarItemPedido() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			// Mockando EntityTransaction
			EntityTransaction mockTransaction = mock(EntityTransaction.class);
			when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
			when(mockTransaction.isActive()).thenReturn(true);

			// Mockando o comportamento do EntityManager para o objeto ItemPedido
			ItemPedido itemPedido = new ItemPedido(1L, new Pedido(), new Produto(), 5, new BigDecimal("500.00"));
			when(mockEntityManager.contains(itemPedido)).thenReturn(true);

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			pedidoItemDAO.deletarItemPedido(itemPedido);

			verify(mockEntityManager, times(1)).remove(itemPedido);
			verify(mockTransaction, times(1)).commit();
		}
	}

	@Test
	void testBuscarTodosItens() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			TypedQuery<ItemPedido> mockQuery = mock(TypedQuery.class);
			when(mockEntityManager.createQuery(anyString(), eq(ItemPedido.class))).thenReturn(mockQuery);

			List<ItemPedido> mockItens = List.of(
					new ItemPedido(1L, new Pedido(), new Produto(), 5, new BigDecimal("500.00")),
					new ItemPedido(2L, new Pedido(), new Produto(), 3, new BigDecimal("300.00")));

			when(mockQuery.getResultList()).thenReturn(mockItens);

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			List<ItemPedido> result = pedidoItemDAO.buscarTodosItens();

			assertNotNull(result);
			assertEquals(2, result.size());
			verify(mockEntityManager, times(1)).createQuery(anyString(), eq(ItemPedido.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

	@Test
	void testBuscarTodosItens_NoResults() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			TypedQuery<ItemPedido> mockQuery = mock(TypedQuery.class);
			when(mockEntityManager.createQuery(anyString(), eq(ItemPedido.class))).thenReturn(mockQuery);
			when(mockQuery.getResultList()).thenReturn(List.of());

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			List<ItemPedido> result = pedidoItemDAO.buscarTodosItens();

			assertNotNull(result);
			assertTrue(result.isEmpty());
			verify(mockEntityManager, times(1)).createQuery(anyString(), eq(ItemPedido.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

	@Test
	void testBuscarItensPorPedidoId() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			TypedQuery<ItemPedido> mockQuery = mock(TypedQuery.class);
			when(mockEntityManager.createQuery(anyString(), eq(ItemPedido.class))).thenReturn(mockQuery);

			List<ItemPedido> mockItens = List.of(
					new ItemPedido(1L, new Pedido(), new Produto(), 5, new BigDecimal("500.00")),
					new ItemPedido(2L, new Pedido(), new Produto(), 3, new BigDecimal("300.00")));

			when(mockQuery.setParameter(eq("pedidoId"), anyLong())).thenReturn(mockQuery);
			when(mockQuery.getResultList()).thenReturn(mockItens);

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			List<ItemPedido> result = pedidoItemDAO.buscarItensPorPedidoId(1L);

			assertNotNull(result);
			assertEquals(2, result.size());
			verify(mockEntityManager, times(1)).createQuery(anyString(), eq(ItemPedido.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

	@Test
	void testBuscarItensPorPedidoId_NoResults() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			TypedQuery<ItemPedido> mockQuery = mock(TypedQuery.class);
			when(mockEntityManager.createQuery(anyString(), eq(ItemPedido.class))).thenReturn(mockQuery);
			when(mockQuery.setParameter(eq("pedidoId"), anyLong())).thenReturn(mockQuery);
			when(mockQuery.getResultList()).thenReturn(List.of());

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			List<ItemPedido> result = pedidoItemDAO.buscarItensPorPedidoId(1L);

			assertNotNull(result);
			assertTrue(result.isEmpty());
			verify(mockEntityManager, times(1)).createQuery(anyString(), eq(ItemPedido.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

	@Test
	void testDeletarItemPedido_NotManaged() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			// Mockando EntityTransaction
			EntityTransaction mockTransaction = mock(EntityTransaction.class);
			when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

			ItemPedido itemPedido = new ItemPedido(1L, new Pedido(), new Produto(), 5, new BigDecimal("500.00"));
			when(mockEntityManager.contains(itemPedido)).thenReturn(false);
			when(mockEntityManager.merge(itemPedido)).thenReturn(itemPedido);

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			pedidoItemDAO.deletarItemPedido(itemPedido);

			verify(mockEntityManager, times(1)).merge(itemPedido);
			verify(mockEntityManager, times(1)).remove(itemPedido);
			verify(mockTransaction, times(1)).commit();
		}
	}

	@Test
	void testDeletarItemPedido_NullObject() {
		PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> pedidoItemDAO.deletarItemPedido(null));

		assertEquals("ItemPedido não pode ser nulo", exception.getMessage());
	}

	@Test
	void testBuscarItemPorId_ValidId() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Long validId = 1L;
			ItemPedido mockItem = new ItemPedido(validId, new Pedido(), new Produto(), 5, new BigDecimal("500.00"));
			when(mockEntityManager.find(ItemPedido.class, validId)).thenReturn(mockItem);

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			ItemPedido result = pedidoItemDAO.buscarItemPorId(validId);

			assertNotNull(result);
			assertEquals(validId, result.getItemId());
			verify(mockEntityManager, times(1)).find(ItemPedido.class, validId);
		}
	}

	@Test
	void testBuscarItemPorId_InvalidId() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Long invalidId = 99L;
			when(mockEntityManager.find(ItemPedido.class, invalidId)).thenReturn(null);

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			ItemPedido result = pedidoItemDAO.buscarItemPorId(invalidId);

			assertNull(result);
			verify(mockEntityManager, times(1)).find(ItemPedido.class, invalidId);
		}
	}

	@Test
	void testBuscarItemPorId_NullId() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();
			ItemPedido result = pedidoItemDAO.buscarItemPorId(null);

			assertNull(result);
			verify(mockEntityManager, never()).find(any(), any());
		}
	}

	@Test
	void testBuscarItemPorId_EntityManagerException() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Long id = 1L;
			when(mockEntityManager.find(ItemPedido.class, id)).thenThrow(new RuntimeException("Database error"));

			PedidoItemDAO pedidoItemDAO = new PedidoItemDAO();

			RuntimeException exception = assertThrows(RuntimeException.class, () -> pedidoItemDAO.buscarItemPorId(id));

			// Verifica a mensagem da exceção principal
			assertEquals("Erro ao buscar item do pedido por ID.", exception.getMessage());
			// Verifica a mensagem da causa original
			assertNotNull(exception.getCause());
			assertEquals("Database error", exception.getCause().getMessage());

			verify(mockEntityManager, times(1)).find(ItemPedido.class, id);
		}
	}

}
