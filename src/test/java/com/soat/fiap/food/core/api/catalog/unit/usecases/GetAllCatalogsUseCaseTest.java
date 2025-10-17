package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.GetAllCatalogsUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("GetAllCatalogsUseCase - Testes Unitários")
class GetAllCatalogsUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve retornar lista de catálogos quando existirem catálogos cadastrados")
	void shouldReturnCatalogListWhenCatalogsExist() {
		// Arrange
		var catalog1 = CatalogFixture.createValidCatalog();
		catalog1.setId(1L);
		var catalog2 = CatalogFixture.createEmptyCatalog();
		catalog2.setId(2L);
		var expectedCatalogs = List.of(catalog1, catalog2);

		when(catalogGateway.findAll()).thenReturn(expectedCatalogs);

		// Act
		var result = GetAllCatalogsUseCase.getAllCatalogs(catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result).containsExactlyElementsOf(expectedCatalogs);
		assertThat(result.get(0).getName()).isEqualTo("Catálogo Principal");
		assertThat(result.get(1).getName()).isEqualTo("Catálogo Vazio");
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não existirem catálogos cadastrados")
	void shouldReturnEmptyListWhenNoCatalogsExist() {
		// Arrange
		when(catalogGateway.findAll()).thenReturn(List.of());

		// Act
		var result = GetAllCatalogsUseCase.getAllCatalogs(catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();
	}

	@Test @DisplayName("Deve retornar único catálogo quando existir apenas um catálogo cadastrado")
	void shouldReturnSingleCatalogWhenOneCatalogExists() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithCategories();
		catalog.setId(1L);
		var expectedCatalogs = List.of(catalog);

		when(catalogGateway.findAll()).thenReturn(expectedCatalogs);

		// Act
		var result = GetAllCatalogsUseCase.getAllCatalogs(catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(1);
		assertThat(result.getFirst()).isEqualTo(catalog);
		assertThat(result.getFirst().getName()).isEqualTo("Catálogo com Categorias");
		assertThat(result.getFirst().getCategories()).isNotEmpty();
	}
}
