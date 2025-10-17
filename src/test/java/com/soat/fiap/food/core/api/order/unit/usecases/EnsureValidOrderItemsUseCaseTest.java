package com.soat.fiap.food.core.api.order.unit.usecases;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.ProductNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.order.core.application.usecases.EnsureValidOrderItemsUseCase;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;
import com.soat.fiap.food.core.api.shared.fixtures.OrderFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("EnsureValidOrderItemsUseCase - Testes Unitários")
class EnsureValidOrderItemsUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve validar com sucesso quando todos os itens são válidos")
	void shouldValidateSuccessfullyWhenAllItemsAreValid() {
		// Arrange
		var orderItem = OrderFixture.createValidOrderItem(); // Big Mac, 2x, 25.90

		var catalog = CatalogFixture.createCatalogWithProducts();
		// O produto já vem configurado corretamente na fixture

		when(catalogGateway.findByProductId(1L)).thenReturn(Optional.of(catalog));

		// Act & Assert
		assertThatNoException().isThrownBy(() -> EnsureValidOrderItemsUseCase
				.ensureValidOrderItems(Collections.singletonList(orderItem), catalogGateway));

		verify(catalogGateway).findByProductId(1L);
	}

	@Test @DisplayName("Deve lançar exceção quando produto não for encontrado")
	void shouldThrowExceptionWhenProductNotFound() {
		// Arrange
		var orderItem = OrderFixture.createBeverageOrderItem(); // ID 2

		when(catalogGateway.findByProductId(2L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> EnsureValidOrderItemsUseCase
				.ensureValidOrderItems(Collections.singletonList(orderItem), catalogGateway))
				.isInstanceOf(ProductNotFoundException.class)
				.hasMessage("O produto do item do pedido não existe");

		verify(catalogGateway).findByProductId(2L);
	}

	@Test @DisplayName("Deve validar múltiplos itens com sucesso")
	void shouldValidateMultipleItemsSuccessfully() {
		// Arrange
		var orderItem1 = OrderFixture.createValidOrderItem(); // ID 1
		var orderItem2 = OrderFixture.createBeverageOrderItem(); // ID 2
		var orderItems = Arrays.asList(orderItem1, orderItem2);

		var catalog1 = CatalogFixture.createCatalogWithProducts(); // Contém produto ID 1
		var catalog2 = CatalogFixture.createCatalogWithMultipleProducts(); // Contém produto ID 2

		when(catalogGateway.findByProductId(1L)).thenReturn(Optional.of(catalog1));
		when(catalogGateway.findByProductId(2L)).thenReturn(Optional.of(catalog2));

		// Act & Assert
		assertThatNoException()
				.isThrownBy(() -> EnsureValidOrderItemsUseCase.ensureValidOrderItems(orderItems, catalogGateway));

		verify(catalogGateway).findByProductId(1L);
		verify(catalogGateway).findByProductId(2L);
	}
}
