package com.github.jeanbezerra.ecommerce.web.portal.admin.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.ProdutoDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Categoria;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Produto;
import com.github.jeanbezerra.ecommerce.web.portal.admin.helper.HibernateEntityManagerHelper;

import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProdutoDAOTest {

	@BeforeAll
	static void setUp() {
		System.setProperty("env", "test");
	}

	@AfterEach
	void tearDown() {
		Mockito.clearAllCaches();
	}

	@Test
	void testSave_NewProduto() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Categoria categoria = new Categoria(1L, "Eletrônicos");
			Produto novoProduto = new Produto(null, "Smartphone", "Um ótimo smartphone", new BigDecimal("1200.00"), 10,
					"url_imagem", categoria);

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.save(novoProduto);

			verify(mockEntityManager, times(1)).persist(novoProduto);
			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
		}
	}

	@Test
	void testSave_ExistingProduto() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Categoria categoria = new Categoria(1L, "Eletrônicos");
			Produto produtoExistente = new Produto(1L, "Smartphone", "Um ótimo smartphone", new BigDecimal("1200.00"),
					10, "url_imagem", categoria);

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.save(produtoExistente);

			verify(mockEntityManager, times(1)).merge(produtoExistente);
			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
		}
	}

	@Test
	void testDelete_ProdutoExists() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Produto mockProduto = new Produto(1L, "Smartphone", "Um ótimo smartphone", new BigDecimal("1200.00"), 10,
					"url_imagem", null);
			when(mockEntityManager.find(Produto.class, 1L)).thenReturn(mockProduto);

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.delete(1L);

			verify(mockEntityManager, times(1)).find(Produto.class, 1L);
			verify(mockEntityManager, times(1)).remove(mockProduto);
			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
		}
	}

	@Test
	void testDelete_ProdutoNotFound() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			when(mockEntityManager.find(Produto.class, 1L)).thenReturn(null);

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.delete(1L);

			verify(mockEntityManager, times(1)).find(Produto.class, 1L);
			verify(mockEntityManager, never()).remove(any());
			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
		}
	}

	@Test
	void testFindById() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Produto mockProduto = new Produto(1L, "Smartphone", "Um ótimo smartphone", new BigDecimal("1200.00"), 10,
					"url_imagem", null);
			when(mockEntityManager.find(Produto.class, 1L)).thenReturn(mockProduto);

			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto result = produtoDAO.findById(1L);

			assertNotNull(result);
			assertEquals(1L, result.getProdutoId());
			assertEquals("Smartphone", result.getNome());

			verify(mockEntityManager, times(1)).find(Produto.class, 1L);
		}
	}

	@Test
	void testFindAll() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			TypedQuery<Produto> mockQuery = mock(TypedQuery.class);
			when(mockEntityManager.createQuery(anyString(), eq(Produto.class))).thenReturn(mockQuery);

			List<Produto> mockProdutos = List.of(
					new Produto(1L, "Smartphone", "Um ótimo smartphone", new BigDecimal("1200.00"), 10, "url_imagem",
							null),
					new Produto(2L, "Notebook", "Notebook de alta performance", new BigDecimal("3500.00"), 5,
							"url_imagem", null));

			when(mockQuery.getResultList()).thenReturn(mockProdutos);

			ProdutoDAO produtoDAO = new ProdutoDAO();
			List<Produto> result = produtoDAO.findAll();

			assertNotNull(result);
			assertEquals(2, result.size());
			assertEquals("Smartphone", result.get(0).getNome());
			assertEquals("Notebook", result.get(1).getNome());

			verify(mockEntityManager, times(1)).createQuery(anyString(), eq(Produto.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

	@Test
	void testFindAllAsSelectItems() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			TypedQuery<Produto> mockQuery = mock(TypedQuery.class);
			when(mockEntityManager.createQuery(anyString(), eq(Produto.class))).thenReturn(mockQuery);

			List<Produto> mockProdutos = List.of(
					new Produto(1L, "Smartphone", "Um ótimo smartphone", new BigDecimal("1200.00"), 10, "url_imagem",
							null),
					new Produto(2L, "Notebook", "Notebook de alta performance", new BigDecimal("3500.00"), 5,
							"url_imagem", null));

			when(mockQuery.getResultList()).thenReturn(mockProdutos);

			ProdutoDAO produtoDAO = new ProdutoDAO();
			List<SelectItem> result = produtoDAO.findAllAsSelectItems();

			assertNotNull(result);
			assertEquals(2, result.size());
			assertEquals("Smartphone", result.get(0).getLabel());
			assertEquals(1L, result.get(0).getValue());

			verify(mockEntityManager, times(1)).createQuery(anyString(), eq(Produto.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

}
