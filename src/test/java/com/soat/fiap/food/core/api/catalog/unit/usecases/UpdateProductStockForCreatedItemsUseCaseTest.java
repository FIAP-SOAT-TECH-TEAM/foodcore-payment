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

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductStockUpdateInput;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.UpdateProductStockForCreatedItemsUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.StockException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderItemNotFoundException;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("UpdateProductStockForCreatedItemsUseCase - Testes Unitários")
class UpdateProductStockForCreatedItemsUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve atualizar estoque do produto após criação de item do pedido")
	void shouldUpdateProductStockAfterOrderItemCreation() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var productId = 1L;
		var quantityToDecrease = 2;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, quantityToDecrease);

		// Definir estoque inicial do produto
		catalog.updateProductStockQuantity(productId, 10);
		var initialStock = catalog.getProductStockQuantity(productId);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductStockForCreatedItemsUseCase.updateStockForCreatedItem(productStockItemInput,
				catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getProductStockQuantity(productId)).isEqualTo(initialStock - quantityToDecrease);
		verify(catalogGateway).findByProductId(productId);
	}

	@Test @DisplayName("Deve atualizar estoque corretamente com quantidade grande")
	void shouldUpdateStockCorrectlyWithLargeQuantity() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var productId = 1L;
		var quantityToDecrease = 5;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, quantityToDecrease);

		// Definir estoque inicial do produto
		catalog.updateProductStockQuantity(productId, 20);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductStockForCreatedItemsUseCase.updateStockForCreatedItem(productStockItemInput,
				catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getProductStockQuantity(productId)).isEqualTo(15);
		verify(catalogGateway).findByProductId(productId);
	}

	@Test @DisplayName("Deve lançar exceção quando quantidade solicitada resulta em estoque negativo")
	void shouldThrowExceptionWhenRequestedQuantityResultsInNegativeStock() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var productId = 1L;
		var quantityToDecrease = 15;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, quantityToDecrease);

		// Definir estoque inicial do produto
		catalog.updateProductStockQuantity(productId, 10);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.of(catalog));

		// Act & Assert
		assertThatThrownBy(() -> UpdateProductStockForCreatedItemsUseCase
				.updateStockForCreatedItem(productStockItemInput, catalogGateway)).isInstanceOf(StockException.class)
				.hasMessage("A quantidade de estoque deve ser positiva");

		verify(catalogGateway).findByProductId(productId);
	}

	@Test @DisplayName("Deve lançar exceção quando item de produto for nulo")
	void shouldThrowExceptionWhenProductItemIsNull() {
		// Act & Assert
		assertThatThrownBy(
				() -> UpdateProductStockForCreatedItemsUseCase.updateStockForCreatedItem(null, catalogGateway))
				.isInstanceOf(OrderItemNotFoundException.class)
				.hasMessage("Itens de pedido é nulo. Não é possível efetuar atualização de quantidade em estoque.");
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo do produto não for encontrado")
	void shouldThrowExceptionWhenProductCatalogNotFound() {
		// Arrange
		var productId = 999L;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, 2);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> UpdateProductStockForCreatedItemsUseCase
				.updateStockForCreatedItem(productStockItemInput, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class)
				.hasMessage(
						"Catálogo do produto do item de pedido não encontrado. Não é possível atualizar quantidade em estoque.");

		verify(catalogGateway).findByProductId(productId);
	}

	@Test @DisplayName("Deve atualizar estoque com quantidade mínima válida")
	void shouldUpdateStockWithMinimumValidQuantity() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var productId = 1L;
		var quantityToDecrease = 1;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, quantityToDecrease);

		// Definir estoque inicial do produto
		catalog.updateProductStockQuantity(productId, 5);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductStockForCreatedItemsUseCase.updateStockForCreatedItem(productStockItemInput,
				catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getProductStockQuantity(productId)).isEqualTo(4);
		verify(catalogGateway).findByProductId(productId);
	}
}
