package com.soat.fiap.food.core.api.catalog.unit.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CatalogMapper;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CatalogRequest;

@DisplayName("CatalogMapper - Testes Unitários")
class CatalogDTOMapperTest {

	@Test @DisplayName("Deve mapear CatalogRequest para CatalogInput")
	void shouldMapCatalogRequestToCatalogInput() {
		// Arrange
		var catalogRequest = CatalogRequest.builder().name("Catálogo Principal").build();

		// Act
		var catalogInput = assertDoesNotThrow(() -> CatalogMapper.toInput(catalogRequest));

		// Assert
		assertThat(catalogInput).isNotNull();
		assertThat(catalogInput.name()).isEqualTo("Catálogo Principal");
	}

	@Test @DisplayName("Deve mapear CatalogRequest com nome nulo")
	void shouldMapCatalogRequestWithNullName() {
		// Arrange
		var catalogRequest = CatalogRequest.builder().build();

		// Act
		var catalogInput = assertDoesNotThrow(() -> CatalogMapper.toInput(catalogRequest));

		// Assert
		assertThat(catalogInput).isNotNull();
	}

	@Test @DisplayName("Deve mapear CatalogRequest com nome vazio")
	void shouldMapCatalogRequestWithEmptyName() {
		// Arrange
		var catalogRequest = CatalogRequest.builder().name("").build();

		// Act
		var catalogInput = assertDoesNotThrow(() -> CatalogMapper.toInput(catalogRequest));

		// Assert
		assertThat(catalogInput).isNotNull();
		assertThat(catalogInput.name()).isEmpty();
	}
}
