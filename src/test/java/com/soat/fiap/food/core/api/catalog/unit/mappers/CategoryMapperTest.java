package com.soat.fiap.food.core.api.catalog.unit.mappers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CategoryInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CategoryMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CategoryRequest;

@DisplayName("CategoryMapper - Testes Unitários")
class CategoryMapperTest {

	@Test @DisplayName("Deve mapear CategoryRequest para CategoryInput com sucesso")
	void shouldMapCategoryRequestToInput() {
		// Arrange
		CategoryRequest request = new CategoryRequest();
		request.setCatalogId(1L);
		request.setName("Lanches");
		request.setDescription("Categoria de lanches diversos");
		request.setActive(true);
		request.setDisplayOrder(1);

		// Act
		CategoryInput result = CategoryMapper.toInput(request);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.catalogId());
		assertEquals("Lanches", result.name());
		assertEquals("Categoria de lanches diversos", result.description());
		assertTrue(result.active());
		assertEquals(1, result.displayOrder());
	}

	@Test @DisplayName("Deve mapear CategoryInput para Category domain com sucesso")
	void shouldMapCategoryInputToDomain() {
		// Arrange
		CategoryInput input = new CategoryInput(2L, "Bebidas", "Categoria de bebidas variadas", false, 2);

		// Act
		Category result = CategoryMapper.toDomain(input);

		// Assert
		assertNotNull(result);
		assertEquals("Bebidas", result.getDetails().name());
		assertEquals("Categoria de bebidas variadas", result.getDetails().description());
		assertFalse(result.isActive());
		assertEquals(2, result.getDisplayOrder());
		assertNotNull(result.getImageUrl());
		assertEquals("", result.getImageUrl().imageUrl());
	}

	@Test @DisplayName("Deve mapear CategoryRequest com valores nulos")
	void shouldMapCategoryRequestWithNullValues() {
		// Arrange
		CategoryRequest request = new CategoryRequest();
		request.setCatalogId(null);
		request.setName(null);
		request.setDescription(null);
		request.setActive(false);
		request.setDisplayOrder(null);

		// Act
		CategoryInput result = CategoryMapper.toInput(request);

		// Assert
		assertNotNull(result);
		assertNull(result.catalogId());
		assertNull(result.name());
		assertNull(result.description());
		assertFalse(result.active());
		assertNull(result.displayOrder());
	}
}
