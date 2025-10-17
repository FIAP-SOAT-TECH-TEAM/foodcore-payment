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

import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.GetCategoryByIdUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("GetCategoryByIdUseCase - Testes Unitários")
class GetCategoryByIdUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve retornar categoria quando catálogo e categoria existem")
	void shouldReturnCategoryWhenCatalogAndCategoryExist() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;
		var catalog = CatalogFixture.createCatalogWithProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetCategoryByIdUseCase.getCategoryById(catalogId, categoryId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(categoryId);
		assertThat(result.getName()).isEqualTo("Lanches");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não for encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		var catalogId = 999L;
		var categoryId = 1L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> GetCategoryByIdUseCase.getCategoryById(catalogId, categoryId, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class)
				.hasMessage("Catalogo não encontrado com id: 999");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve retornar categoria específica do catálogo")
	void shouldReturnSpecificCategoryFromCatalog() {
		// Arrange
		var catalogId = 2L;
		var categoryId = 2L;
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetCategoryByIdUseCase.getCategoryById(catalogId, categoryId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(categoryId);
		assertThat(result.getName()).isEqualTo("Bebidas");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve validar que a categoria pertence ao catálogo correto")
	void shouldValidateCategoryBelongsToCorrectCatalog() {
		// Arrange
		var catalogId = 3L;
		var categoryId = 1L;
		var catalog = CatalogFixture.createCatalogWithProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetCategoryByIdUseCase.getCategoryById(catalogId, categoryId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(categoryId);
		assertThat(result.getProducts()).isNotEmpty();

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve retornar categoria mesmo quando vazia de produtos")
	void shouldReturnCategoryEvenWhenEmptyOfProducts() {
		// Arrange
		var catalogId = 4L;
		var categoryId = 1L;
		var catalog = CatalogFixture.createCatalogWithInactiveCategory();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetCategoryByIdUseCase.getCategoryById(catalogId, categoryId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(categoryId);
		assertThat(result.getName()).isEqualTo("Categoria Inativa");
		assertThat(result.isActive()).isFalse();

		verify(catalogGateway).findById(catalogId);
	}
}
