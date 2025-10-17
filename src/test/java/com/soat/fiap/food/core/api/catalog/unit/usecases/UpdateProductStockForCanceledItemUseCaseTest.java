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
import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.UpdateProductStockForCanceledItemUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderItemNotFoundException;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("UpdateProductStockForCanceledItemUseCase - Testes Unitários")
class UpdateProductStockForCanceledItemUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve atualizar estoque do produto após cancelamento de item do pedido")
	void shouldUpdateProductStockAfterOrderItemCancellation() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var productId = 1L;
		var quantityToIncrease = 2;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, quantityToIncrease);

		// Definir estoque inicial do produto
		catalog.updateProductStockQuantity(productId, 8);
		var initialStock = catalog.getProductStockQuantity(productId);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductStockForCanceledItemUseCase.updateStockForCanceledItem(productStockItemInput,
				catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getProductStockQuantity(productId)).isEqualTo(initialStock + quantityToIncrease);
		verify(catalogGateway).findByProductId(productId);
	}

	@Test @DisplayName("Deve atualizar estoque corretamente com quantidade grande cancelada")
	void shouldUpdateStockCorrectlyWithLargeCancelledQuantity() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var productId = 1L;
		var quantityToIncrease = 10;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, quantityToIncrease);

		// Definir estoque inicial do produto
		catalog.updateProductStockQuantity(productId, 5);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductStockForCanceledItemUseCase.updateStockForCanceledItem(productStockItemInput,
				catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getProductStockQuantity(productId)).isEqualTo(15);
		verify(catalogGateway).findByProductId(productId);
	}

	@Test @DisplayName("Deve restaurar estoque corretamente com quantidade adequada")
	void shouldRestoreStockCorrectlyWithAdequateQuantity() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var productId = 1L;
		var quantityToIncrease = 3;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, quantityToIncrease);

		// Definir estoque inicial baixo do produto
		catalog.updateProductStockQuantity(productId, 2);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductStockForCanceledItemUseCase.updateStockForCanceledItem(productStockItemInput,
				catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getProductStockQuantity(productId)).isEqualTo(5);
		verify(catalogGateway).findByProductId(productId);
	}

	@Test @DisplayName("Deve lançar exceção quando item de produto for nulo")
	void shouldThrowExceptionWhenProductItemIsNull() {
		// Act & Assert
		assertThatThrownBy(
				() -> UpdateProductStockForCanceledItemUseCase.updateStockForCanceledItem(null, catalogGateway))
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
		assertThatThrownBy(() -> UpdateProductStockForCanceledItemUseCase
				.updateStockForCanceledItem(productStockItemInput, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class)
				.hasMessage(
						"Catálogo do produto do item de pedido não encontrado. Não é possível atualizar quantidade em estoque.");

		verify(catalogGateway).findByProductId(productId);
	}

	@Test @DisplayName("Deve atualizar estoque com quantidade mínima válida cancelada")
	void shouldUpdateStockWithMinimumValidCancelledQuantity() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var productId = 1L;
		var quantityToIncrease = 1;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, quantityToIncrease);

		// Definir estoque inicial do produto
		catalog.updateProductStockQuantity(productId, 4);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductStockForCanceledItemUseCase.updateStockForCanceledItem(productStockItemInput,
				catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getProductStockQuantity(productId)).isEqualTo(5);
		verify(catalogGateway).findByProductId(productId);
	}

	@Test @DisplayName("Deve restaurar estoque para valor positivo quando estava zerado")
	void shouldRestoreStockToPositiveValueWhenWasZero() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var productId = 1L;
		var quantityToIncrease = 5;
		var productStockItemInput = new ProductStockUpdateInput.ProductStockItemInput(productId, quantityToIncrease);

		// Definir estoque inicial zerado do produto
		catalog.updateProductStockQuantity(productId, 0);

		when(catalogGateway.findByProductId(productId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductStockForCanceledItemUseCase.updateStockForCanceledItem(productStockItemInput,
				catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getProductStockQuantity(productId)).isEqualTo(5);
		verify(catalogGateway).findByProductId(productId);
	}
}
