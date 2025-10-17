package com.soat.fiap.food.core.api.catalog.unit.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CategoryMapper;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CategoryRequest;

@DisplayName("CategoryMapper - Testes Unitários")
class CategoryMapperTest {

	@Test @DisplayName("Deve mapear CategoryRequest para CategoryInput")
	void shouldMapCategoryRequestToCategoryInput() {
		// Arrange
		var categoryRequest = CategoryRequest.builder().name("Lanches").description("Categoria de lanches").build();

		// Act
		var categoryInput = assertDoesNotThrow(() -> CategoryMapper.toInput(categoryRequest));

		// Assert
		assertThat(categoryInput).isNotNull();
		assertThat(categoryInput.name()).isEqualTo("Lanches");
		assertThat(categoryInput.description()).isEqualTo("Categoria de lanches");
	}

	@Test @DisplayName("Deve mapear CategoryRequest com valores nulos")
	void shouldMapCategoryRequestWithNullValues() {
		// Arrange
		var categoryRequest = CategoryRequest.builder().build();

		// Act
		var categoryInput = assertDoesNotThrow(() -> CategoryMapper.toInput(categoryRequest));

		// Assert
		assertThat(categoryInput).isNotNull();
	}

	@Test @DisplayName("Deve mapear CategoryRequest com nome em branco")
	void shouldMapCategoryRequestWithBlankName() {
		// Arrange
		var categoryRequest = CategoryRequest.builder().name("").description("").build();

		// Act
		var categoryInput = assertDoesNotThrow(() -> CategoryMapper.toInput(categoryRequest));

		// Assert
		assertThat(categoryInput).isNotNull();
		assertThat(categoryInput.name()).isEmpty();
		assertThat(categoryInput.description()).isEmpty();
	}
}
