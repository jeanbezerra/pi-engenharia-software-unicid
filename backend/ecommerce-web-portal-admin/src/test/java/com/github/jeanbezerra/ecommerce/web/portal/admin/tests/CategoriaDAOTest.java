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

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.CategoriaDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Categoria;
import com.github.jeanbezerra.ecommerce.web.portal.admin.helper.HibernateEntityManagerHelper;

import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class CategoriaDAOTest {

	@BeforeAll
	static void setUp() {
		System.setProperty("env", "test");
	}

	@AfterEach
	void tearDown() {
		Mockito.clearAllCaches();
	}

	@Test
	void testSave_NewCategoria() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);

			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);
			mockedHelper.when(HibernateEntityManagerHelper::beginTransaction).thenAnswer(invocation -> null);
			mockedHelper.when(HibernateEntityManagerHelper::commit).thenAnswer(invocation -> null);

			Categoria novaCategoria = new Categoria(null, "Nova Categoria");
			CategoriaDAO categoriaDAO = new CategoriaDAO();

			categoriaDAO.save(novaCategoria);

			// Verifica chamadas no mock
			verify(mockEntityManager, times(1)).persist(novaCategoria);
			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
		}
	}

	@Test
	void testSave_ExistingCategoria() {
		EntityManager mockEntityManager = mock(EntityManager.class);
		CategoriaDAO categoriaDAO = new CategoriaDAO();

		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Categoria categoriaExistente = new Categoria(1L, "Categoria Atualizada");
			when(mockEntityManager.merge(categoriaExistente)).thenReturn(categoriaExistente);

			categoriaDAO.save(categoriaExistente);

			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
			verify(mockEntityManager, times(1)).merge(categoriaExistente);
		}
	}

	@Test
	void testDelete_CategoriaExists() {
		EntityManager mockEntityManager = mock(EntityManager.class);
		CategoriaDAO categoriaDAO = new CategoriaDAO();

		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Categoria categoria = new Categoria(1L, "Para Deletar");
			when(mockEntityManager.find(Categoria.class, 1L)).thenReturn(categoria);

			categoriaDAO.delete(1L);

			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
			verify(mockEntityManager, times(1)).remove(categoria);
		}
	}

	@Test
	void testDelete_CategoriaDoesNotExist() {
		EntityManager mockEntityManager = mock(EntityManager.class);
		CategoriaDAO categoriaDAO = new CategoriaDAO();

		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			when(mockEntityManager.find(Categoria.class, 999L)).thenReturn(null);

			categoriaDAO.delete(999L);

			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
			verify(mockEntityManager, never()).remove(any());
		}
	}

	@Test
	void testFindAll() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			TypedQuery<Categoria> mockQuery = mock(TypedQuery.class);
			when(mockEntityManager.createQuery(anyString(), eq(Categoria.class))).thenReturn(mockQuery);

			List<Categoria> mockCategorias = List.of(new Categoria(1L, "Categoria 1"),
					new Categoria(2L, "Categoria 2"));

			when(mockQuery.getResultList()).thenReturn(mockCategorias);

			CategoriaDAO categoriaDAO = new CategoriaDAO();
			List<Categoria> result = categoriaDAO.findAll();

			assertNotNull(result);
			assertEquals(2, result.size());
			assertEquals("Categoria 1", result.get(0).getNome());
			assertEquals("Categoria 2", result.get(1).getNome());

			verify(mockEntityManager, times(1)).createQuery(anyString(), eq(Categoria.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

	@Test
	void testFindById() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			Categoria mockCategoria = new Categoria(1L, "Categoria 1");
			when(mockEntityManager.find(Categoria.class, 1L)).thenReturn(mockCategoria);

			CategoriaDAO categoriaDAO = new CategoriaDAO();
			Categoria result = categoriaDAO.findById(1L);

			assertNotNull(result);
			assertEquals(1L, result.getCategoriaId());
			assertEquals("Categoria 1", result.getNome());

			verify(mockEntityManager, times(1)).find(Categoria.class, 1L);
		}
	}

	@Test
	void testDelete_CategoriaNotFound() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			when(mockEntityManager.find(Categoria.class, 1L)).thenReturn(null);

			CategoriaDAO categoriaDAO = new CategoriaDAO();
			categoriaDAO.delete(1L);

			verify(mockEntityManager, times(1)).find(Categoria.class, 1L);
			verify(mockEntityManager, never()).remove(any());
			mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
			mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
		}
	}

	@Test
	void testFindAllAsSelectItems() {
		try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
			EntityManager mockEntityManager = mock(EntityManager.class);
			mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

			TypedQuery<Categoria> mockQuery = mock(TypedQuery.class);
			when(mockEntityManager.createQuery(anyString(), eq(Categoria.class))).thenReturn(mockQuery);

			List<Categoria> mockCategorias = List.of(new Categoria(1L, "Categoria 1"),
					new Categoria(2L, "Categoria 2"));

			when(mockQuery.getResultList()).thenReturn(mockCategorias);

			CategoriaDAO categoriaDAO = new CategoriaDAO();
			List<SelectItem> result = categoriaDAO.findAllAsSelectItems();

			assertNotNull(result);
			assertEquals(2, result.size());
			assertEquals("Categoria 1", result.get(0).getLabel());
			assertEquals(1L, result.get(0).getValue());

			verify(mockEntityManager, times(1)).createQuery(anyString(), eq(Categoria.class));
			verify(mockQuery, times(1)).getResultList();
		}
	}

}