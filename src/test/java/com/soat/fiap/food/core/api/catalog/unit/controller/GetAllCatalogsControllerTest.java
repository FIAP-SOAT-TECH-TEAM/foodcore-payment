package com.soat.fiap.food.core.api.catalog.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.catalog.GetAllCatalogsController;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers.CatalogDTOMapper;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("GetAllCatalogsController - Testes Unitários")
class GetAllCatalogsControllerTest {

	@Mock
	private CatalogDataSource catalogDataSource;

	@Test @DisplayName("Deve retornar lista de catálogos com sucesso")
	void shouldReturnCatalogListSuccessfully() {
		// Arrange
		var catalogs = List.of(CatalogFixture.createValidCatalog(), CatalogFixture.createEmptyCatalog());
		var catalogsDTOs = catalogs.stream().map(CatalogDTOMapper::toDTO).toList();

		when(catalogDataSource.findAll()).thenReturn(catalogsDTOs);

		// Act
		var response = assertDoesNotThrow(() -> GetAllCatalogsController.getAllCatalogs(catalogDataSource));

		// Assert
		assertThat(response).isNotNull();
		assertThat(response).hasSize(2);
		verify(catalogDataSource).findAll();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há catálogos")
	void shouldReturnEmptyListWhenNoCatalogsExist() {
		// Arrange
		when(catalogDataSource.findAll()).thenReturn(List.of());

		// Act
		var response = GetAllCatalogsController.getAllCatalogs(catalogDataSource);

		// Assert
		assertThat(response).isNotNull();
		assertThat(response).isEmpty();
		verify(catalogDataSource).findAll();
	}

	@Test @DisplayName("Deve executar busca sem lançar exceção")
	void shouldExecuteSearchWithoutThrowingException() {
		// Arrange
		var catalogs = List.of(CatalogFixture.createCatalogWithCategories());
		var catalogsDTOs = catalogs.stream().map(CatalogDTOMapper::toDTO).toList();

		when(catalogDataSource.findAll()).thenReturn(catalogsDTOs);

		// Act & Assert
		assertDoesNotThrow(() -> GetAllCatalogsController.getAllCatalogs(catalogDataSource));

		verify(catalogDataSource).findAll();
	}
}
