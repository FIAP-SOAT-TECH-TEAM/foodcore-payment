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

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductInput;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.AddProductToCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("AddProductToCategoryUseCase - Testes Unitários")
class AddProductToCategoryUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve adicionar produto à categoria com sucesso")
	void shouldAddProductToCategorySuccessfully() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();
		var catalogId = 1L;
		var productInput = new ProductInput(1L, "Novo Produto", "Descrição do produto", new BigDecimal("15.99"), 10, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = AddProductToCategoryUseCase.addProductToCategory(catalogId, productInput, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalog.getId());
		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		var catalogId = 999L;
		var productInput = new ProductInput(1L, "Produto Teste", "Descrição", new BigDecimal("10.00"), 5, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(
				() -> AddProductToCategoryUseCase.addProductToCategory(catalogId, productInput, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class);

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve executar adição sem exceções para produto válido")
	void shouldExecuteAdditionWithoutExceptionsForValidProduct() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();
		var catalogId = 1L;
		var productInput = new ProductInput(1L, "Produto Válido", "Descrição válida", new BigDecimal("20.00"), 15, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = AddProductToCategoryUseCase.addProductToCategory(catalogId, productInput, catalogGateway);

		// Assert
		assertThat(result).isEqualTo(catalog);
		verify(catalogGateway).findById(catalogId);
	}
}
