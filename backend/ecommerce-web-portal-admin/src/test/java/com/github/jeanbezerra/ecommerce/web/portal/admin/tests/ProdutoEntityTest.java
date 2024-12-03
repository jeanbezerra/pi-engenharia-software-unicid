package com.github.jeanbezerra.ecommerce.web.portal.admin.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.github.jeanbezerra.ecommerce.web.portal.admin.entity.Produto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ProdutoEntityTest {

//	@Test
//	void testProdutoEntity() {
//		Produto produto = new Produto();
//		produto.setProdutoId(1L);
//		produto.setNome("Notebook");
//		produto.setPreco(new BigDecimal(1500.00).setScale(1));
//
//		assertEquals(1L, produto.getProdutoId());
//		assertEquals("Notebook", produto.getNome());
//		assertEquals(1500.00, Double.parseDouble(produto.getPreco().toString()));
//	}
//
//	@Test
//	void testProdutoEqualsAndHashCode() {
//		Produto produto1 = new Produto();
//		produto1.setProdutoId(1L);
//		produto1.setNome("Notebook");
//
//		Produto produto2 = new Produto();
//		produto2.setProdutoId(1L);
//		produto2.setNome("Notebook");
//
//		Produto produto3 = new Produto();
//		produto3.setProdutoId(2L);
//		produto3.setNome("Smartphone");
//
//		// Testa igualdade lógica
//		assertEquals(produto1, produto2); // Devem ser iguais
//		assertNotEquals(produto1, produto3); // Devem ser diferentes
//
//		// Testa o hashCode
//		assertEquals(produto1.hashCode(), produto2.hashCode());
//		assertNotEquals(produto1.hashCode(), produto3.hashCode());
//	}
//
//	@Test
//	void testProdutoValidations() {
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		Validator validator = factory.getValidator();
//
//		Produto produto = new Produto();
//		produto.setNome(null); // Nome não deve ser nulo
//		produto.setPreco(new BigDecimal("-50.00")); // Preço não deve ser negativo
//
//		var violations = validator.validate(produto);
//
//		assertFalse(violations.isEmpty());
//		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
//		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("preco")));
//	}

}
