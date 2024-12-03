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

import com.github.jeanbezerra.ecommerce.web.portal.admin.dao.ClienteDAO;
import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Cliente;
import com.github.jeanbezerra.ecommerce.web.portal.admin.helper.HibernateEntityManagerHelper;

import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClienteDAOTest {
	
	@BeforeAll
	static void setUp() {
		System.setProperty("env", "test");
	}

	@AfterEach
	void tearDown() {
		Mockito.clearAllCaches();
	}
	
	@Test
    void testSave_NewCliente() {
        try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
            EntityManager mockEntityManager = mock(EntityManager.class);
            mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

            Cliente novoCliente = new Cliente(null, "João Silva", "joao@email.com", "password123", "salt123", "Rua 123", "123456789");

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.save(novoCliente);

            verify(mockEntityManager, times(1)).persist(novoCliente);
            mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
            mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
        }
    }

    @Test
    void testSave_ExistingCliente() {
        try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
            EntityManager mockEntityManager = mock(EntityManager.class);
            mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

            Cliente clienteExistente = new Cliente(1L, "João Silva", "joao@email.com", "password123", "salt123", "Rua 123", "123456789");

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.save(clienteExistente);

            verify(mockEntityManager, times(1)).merge(clienteExistente);
            mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
            mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
        }
    }

    @Test
    void testDelete_ClienteExists() {
        try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
            EntityManager mockEntityManager = mock(EntityManager.class);
            mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

            Cliente mockCliente = new Cliente(1L, "João Silva", "joao@email.com", "password123", "salt123", "Rua 123", "123456789");
            when(mockEntityManager.find(Cliente.class, 1L)).thenReturn(mockCliente);

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.delete(1L);

            verify(mockEntityManager, times(1)).find(Cliente.class, 1L);
            verify(mockEntityManager, times(1)).remove(mockCliente);
            mockedHelper.verify(HibernateEntityManagerHelper::beginTransaction, times(1));
            mockedHelper.verify(HibernateEntityManagerHelper::commit, times(1));
        }
    }

    @Test
    void testDelete_ClienteNotFound() {
        try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
            EntityManager mockEntityManager = mock(EntityManager.class);
            mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

            when(mockEntityManager.find(Cliente.class, 1L)).thenReturn(null);

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.delete(1L);

            verify(mockEntityManager, times(1)).find(Cliente.class, 1L);
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

            Cliente mockCliente = new Cliente(1L, "João Silva", "joao@email.com", "password123", "salt123", "Rua 123", "123456789");
            when(mockEntityManager.find(Cliente.class, 1L)).thenReturn(mockCliente);

            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente result = clienteDAO.findById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getClienteId());
            assertEquals("João Silva", result.getNome());

            verify(mockEntityManager, times(1)).find(Cliente.class, 1L);
        }
    }

    @Test
    void testFindAll() {
        try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
            EntityManager mockEntityManager = mock(EntityManager.class);
            mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

            TypedQuery<Cliente> mockQuery = mock(TypedQuery.class);
            when(mockEntityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(mockQuery);

            List<Cliente> mockClientes = List.of(
                new Cliente(1L, "João Silva", "joao@email.com", "password123", "salt123", "Rua 123", "123456789"),
                new Cliente(2L, "Maria Silva", "maria@email.com", "password456", "salt456", "Rua 456", "987654321")
            );

            when(mockQuery.getResultList()).thenReturn(mockClientes);

            ClienteDAO clienteDAO = new ClienteDAO();
            List<Cliente> result = clienteDAO.findAll();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("João Silva", result.get(0).getNome());
            assertEquals("Maria Silva", result.get(1).getNome());

            verify(mockEntityManager, times(1)).createQuery(anyString(), eq(Cliente.class));
            verify(mockQuery, times(1)).getResultList();
        }
    }

    @Test
    void testFindAllAsSelectItems() {
        try (MockedStatic<HibernateEntityManagerHelper> mockedHelper = mockStatic(HibernateEntityManagerHelper.class)) {
            EntityManager mockEntityManager = mock(EntityManager.class);
            mockedHelper.when(HibernateEntityManagerHelper::getEntityManager).thenReturn(mockEntityManager);

            TypedQuery<Cliente> mockQuery = mock(TypedQuery.class);
            when(mockEntityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(mockQuery);

            List<Cliente> mockClientes = List.of(
                new Cliente(1L, "João Silva", "joao@email.com", "password123", "salt123", "Rua 123", "123456789"),
                new Cliente(2L, "Maria Silva", "maria@email.com", "password456", "salt456", "Rua 456", "987654321")
            );

            when(mockQuery.getResultList()).thenReturn(mockClientes);

            ClienteDAO clienteDAO = new ClienteDAO();
            List<SelectItem> result = clienteDAO.findAllAsSelectItems();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("João Silva", result.get(0).getLabel());
            assertEquals(1L, result.get(0).getValue());

            verify(mockEntityManager, times(1)).createQuery(anyString(), eq(Cliente.class));
            verify(mockQuery, times(1)).getResultList();
        }
    }

}
