package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.GetAllProductsUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("GetAllProductsUseCase - Testes Unitários")
class GetAllProductsUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve buscar produtos de uma categoria com sucesso")
	void shouldSearchProductsFromCategorySuccessfully() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetAllProductsUseCase.getAllProducts(catalogId, categoryId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não for encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> GetAllProductsUseCase.getAllProducts(catalogId, categoryId, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class);

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve chamar o gateway apenas uma vez")
	void shouldCallGatewayOnlyOnce() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		GetAllProductsUseCase.getAllProducts(catalogId, categoryId, catalogGateway);

		// Assert
		verify(catalogGateway, times(1)).findById(catalogId);
	}
}
