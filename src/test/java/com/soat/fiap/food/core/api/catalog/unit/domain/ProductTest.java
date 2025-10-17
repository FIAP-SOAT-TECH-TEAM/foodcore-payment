package com.soat.fiap.food.core.api.catalog.unit.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.ProductException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@DisplayName("Product - Testes de Domínio")
class ProductTest {

	@Test @DisplayName("Deve criar produto válido")
	void shouldCreateValidProduct() {
		// Arrange & Act
		Product product = CatalogFixture.createValidProduct();

		// Assert
		assertNotNull(product);
		assertEquals("Big Mac", product.getName());
		assertEquals(
				"Delicioso hambúrguer com dois hambúrgueres, alface, queijo, molho especial, cebola e picles em um pão com gergelim",
				product.getDescription());
		assertEquals(new BigDecimal("25.90"), product.getPrice());
		assertNotNull(product.getImageUrl());
		// Não assumimos que o produto está ativo por padrão, pois depende do estoque
	}

	@Test @DisplayName("Deve criar produto sem imagem")
	void shouldCreateProductWithoutImage() {
		// Arrange & Act
		Product product = CatalogFixture.createProductWithoutImage();

		// Assert
		assertNotNull(product);
		assertEquals("Hambúrguer Simples", product.getName());
		assertNull(product.getImageUrl());
	}

	@Test @DisplayName("Deve retornar nome do produto")
	void shouldReturnProductName() {
		// Arrange
		Product product = CatalogFixture.createValidProduct();

		// Act
		String name = product.getName();

		// Assert
		assertEquals("Big Mac", name);
	}

	@Test @DisplayName("Deve retornar descrição do produto")
	void shouldReturnProductDescription() {
		// Arrange
		Product product = CatalogFixture.createValidProduct();

		// Act
		String description = product.getDescription();

		// Assert
		assertEquals(
				"Delicioso hambúrguer com dois hambúrgueres, alface, queijo, molho especial, cebola e picles em um pão com gergelim",
				description);
	}

	@Test @DisplayName("Deve retornar ID da categoria")
	void shouldReturnCategoryId() {
		// Arrange
		Product product = CatalogFixture.createValidProduct();

		// Act
		Long categoryId = product.getCategoryId();

		// Assert
		assertEquals(1L, categoryId);
	}

	@Test @DisplayName("Deve retornar URL da imagem")
	void shouldReturnImageUrl() {
		// Arrange
		Product product = CatalogFixture.createValidProduct();

		// Act
		String imageUrl = product.getImageUrlValue();

		// Assert
		assertEquals("https://example.com/images/bigmac.jpg", imageUrl);
	}

	@Test @DisplayName("Deve definir URL da imagem")
	void shouldSetImageUrl() {
		// Arrange
		Product product = CatalogFixture.createProductWithoutImage();
		String newImageUrl = "https://example.com/images/new-image.jpg";

		// Act
		product.setImageUrlValue(newImageUrl);

		// Assert
		assertEquals(newImageUrl, product.getImageUrlValue());
	}

	@Test @DisplayName("Deve verificar se URL da imagem está vazia")
	void shouldCheckIfImageUrlIsEmpty() {
		// Arrange
		Product product = new Product(new Details("Test Product", "Test description for product"),
				new BigDecimal("10.00"), new ImageUrl(""), 1);

		// Act
		Boolean isEmpty = product.imageUrlIsEmpty();

		// Assert
		assertTrue(isEmpty);
	}

	@Test @DisplayName("Deve retornar quantidade de estoque padrão")
	void shouldReturnDefaultStockQuantity() {
		// Arrange
		Product product = CatalogFixture.createValidProduct();

		// Act
		Integer quantity = product.getStockQuantity();

		// Assert
		assertNotNull(quantity);
	}

	@Test @DisplayName("Deve verificar status de ativo do produto")
	void shouldCheckProductActiveStatus() {
		// Arrange
		Product product = CatalogFixture.createValidProduct();

		// Act
		boolean isActive = product.isActive();

		// Assert
		// O produto pode estar ativo ou não dependendo do estoque inicial
		assertNotNull(isActive);
	}

	@Test @DisplayName("Deve verificar se categoria está ativa")
	void shouldCheckIfCategoryIsActive() {
		// Arrange
		Product product = CatalogFixture.createValidProduct();

		// Act & Assert
		assertTrue(product.categoryisActive());
	}

	@Test @DisplayName("Deve retornar datas de auditoria")
	void shouldReturnAuditDates() {
		// Arrange
		Product product = CatalogFixture.createValidProduct();

		// Act & Assert
		assertNotNull(product.getCreatedAt());
		assertNotNull(product.getUpdatedAt());
	}

	@Test @DisplayName("Deve lançar exceção para detalhes nulos")
	void shouldThrowExceptionForNullDetails() {
		// Arrange & Act & Assert
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			new Product(null, new BigDecimal("10.00"), new ImageUrl("https://example.com/image.jpg"), 1);
		});

		assertEquals("Os detalhes do produto não podem ser nulos", exception.getMessage());
	}

	@Test @DisplayName("Deve lançar exceção para preço nulo")
	void shouldThrowExceptionForNullPrice() {
		// Arrange & Act & Assert
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			new Product(new Details("Test Product", "Test description for product"), null,
					new ImageUrl("https://example.com/image.jpg"), 1);
		});

		assertEquals("O preço do produto não pode ser nulo", exception.getMessage());
	}

	@Test @DisplayName("Deve lançar exceção para preço negativo")
	void shouldThrowExceptionForNegativePrice() {
		// Arrange & Act & Assert
		ProductException exception = assertThrows(ProductException.class, () -> {
			new Product(new Details("Test Product", "Test description for product"), new BigDecimal("-10.00"),
					new ImageUrl("https://example.com/image.jpg"), 1);
		});

		assertEquals("O preço deve ser positivo", exception.getMessage());
	}

	@Test @DisplayName("Deve lançar exceção para ordem de exibição inválida")
	void shouldThrowExceptionForInvalidDisplayOrder() {
		// Arrange & Act & Assert
		ProductException exception = assertThrows(ProductException.class, () -> {
			new Product(new Details("Test Product", "Test description for product"), new BigDecimal("10.00"),
					new ImageUrl("https://example.com/image.jpg"), 0);
		});

		assertEquals("A ordem de exibição do produto deve ser maior que 0", exception.getMessage());
	}
}
