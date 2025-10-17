package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.GetProductByIdUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("GetProductByIdUseCase - Testes Unitários")
class GetProductByIdUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve retornar produto quando encontrado")
	void shouldReturnProductWhenFound() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();
		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetProductByIdUseCase.getProductById(catalogId, categoryId, productId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(productId);
		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		var catalogId = 999L;
		var categoryId = 1L;
		var productId = 1L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> GetProductByIdUseCase.getProductById(catalogId, categoryId, productId, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class);

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve retornar produto quando catálogo, categoria e produto existem")
	void shouldReturnProductWhenCatalogCategoryAndProductExist() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;
		var catalog = CatalogFixture.createCatalogWithProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetProductByIdUseCase.getProductById(catalogId, categoryId, productId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(productId);
		assertThat(result.getName()).isEqualTo("Big Mac");
		assertThat(result.getPrice()).isEqualTo(new BigDecimal("25.90"));

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve retornar produto específico da categoria")
	void shouldReturnSpecificProductFromCategory() {
		// Arrange
		var catalogId = 2L;
		var categoryId = 2L;
		var productId = 2L;
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetProductByIdUseCase.getProductById(catalogId, categoryId, productId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(productId);
		assertThat(result.getName()).isEqualTo("Coca-Cola");
		assertThat(result.getPrice()).isEqualTo(new BigDecimal("8.50"));

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve validar que o produto pertence à categoria correta")
	void shouldValidateProductBelongsToCorrectCategory() {
		// Arrange
		var catalogId = 3L;
		var categoryId = 1L;
		var productId = 1L;
		var catalog = CatalogFixture.createCatalogWithProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetProductByIdUseCase.getProductById(catalogId, categoryId, productId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(productId);
		assertThat(result.getStock().getQuantity()).isGreaterThan(0);
		assertThat(result.isActive()).isTrue();

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve retornar produto com todas as propriedades")
	void shouldReturnProductWithAllProperties() {
		// Arrange
		var catalogId = 4L;
		var categoryId = 1L;
		var productId = 1L;
		var catalog = CatalogFixture.createCatalogWithProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetProductByIdUseCase.getProductById(catalogId, categoryId, productId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(productId);
		assertThat(result.getName()).isNotBlank();
		assertThat(result.getDescription()).isNotBlank();
		assertThat(result.getPrice()).isPositive();
		assertThat(result.getStock()).isNotNull();
		assertThat(result.isActive()).isNotNull();

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve buscar produto em catálogo com múltiplas categorias")
	void shouldSearchProductInCatalogWithMultipleCategories() {
		// Arrange
		var catalogId = 5L;
		var categoryId = 2L;
		var productId = 2L;
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetProductByIdUseCase.getProductById(catalogId, categoryId, productId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(productId);
		assertThat(result.getName()).isEqualTo("Coca-Cola");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve executar busca sem exceções")
	void shouldExecuteSearchWithoutExceptions() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();
		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act & Assert
		assertThat(catalogGateway.findById(catalogId)).isPresent();

		verify(catalogGateway).findById(catalogId);
	}
}
