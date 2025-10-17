package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.GetAllCategoriesUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("GetAllCategoriesUseCase - Testes Unitários")
class GetAllCategoriesUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve retornar todas as categorias quando catálogo existe")
	void shouldReturnAllCategoriesWhenCatalogExists() {
		// Arrange
		var catalogId = 1L;
		var catalog = CatalogFixture.createCatalogWithCategories();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetAllCategoriesUseCase.getAllCategories(catalogId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getName()).isEqualTo("Lanches");
		assertThat(result.get(1).getName()).isEqualTo("Bebidas");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não for encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		var catalogId = 999L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> GetAllCategoriesUseCase.getAllCategories(catalogId, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class)
				.hasMessage("Catalogo não encontrado com id: 999");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve retornar lista vazia quando catálogo não tem categorias")
	void shouldReturnEmptyListWhenCatalogHasNoCategories() {
		// Arrange
		var catalogId = 2L;
		var catalog = CatalogFixture.createEmptyCatalog();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetAllCategoriesUseCase.getAllCategories(catalogId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve retornar categorias com produtos")
	void shouldReturnCategoriesWithProducts() {
		// Arrange
		var catalogId = 3L;
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetAllCategoriesUseCase.getAllCategories(catalogId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getName()).isEqualTo("Lanches");
		assertThat(result.get(0).getProducts()).isNotEmpty();
		assertThat(result.get(1).getName()).isEqualTo("Bebidas");
		assertThat(result.get(1).getProducts()).isNotEmpty();

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve retornar categorias ativas e inativas")
	void shouldReturnActiveAndInactiveCategories() {
		// Arrange
		var catalogId = 4L;
		var catalog = CatalogFixture.createCatalogWithInactiveCategory();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetAllCategoriesUseCase.getAllCategories(catalogId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getName()).isEqualTo("Categoria Inativa");
		assertThat(result.get(0).isActive()).isFalse();

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve manter ordem das categorias")
	void shouldMaintainCategoryOrder() {
		// Arrange
		var catalogId = 5L;
		var catalog = CatalogFixture.createCatalogWithCategories();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetAllCategoriesUseCase.getAllCategories(catalogId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getId()).isEqualTo(1L);
		assertThat(result.get(1).getId()).isEqualTo(2L);

		verify(catalogGateway).findById(catalogId);
	}
}
