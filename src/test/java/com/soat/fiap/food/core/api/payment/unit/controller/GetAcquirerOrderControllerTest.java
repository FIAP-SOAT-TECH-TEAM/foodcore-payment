package com.soat.fiap.food.core.api.payment.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.controller.web.api.GetAcquirerOrderController;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;

@ExtendWith(MockitoExtension.class) @DisplayName("GetAcquirerOrderController - Testes Unitários")
class GetAcquirerOrderControllerTest {

	@Mock
	private AcquirerSource acquirerSource;

	@Test @DisplayName("Deve retornar detalhes do pedido do adquirente com sucesso")
	void shouldReturnAcquirerOrderDetailsSuccessfully() {
		// Arrange
		var orderId = 123L;
		Map<String, Object> expectedOrder = new HashMap<>();
		expectedOrder.put("id", orderId);
		expectedOrder.put("status", "approved");
		expectedOrder.put("total_amount", 29.99);

		when(acquirerSource.getAcquirerOrder(orderId)).thenReturn(expectedOrder);

		// Act
		var result = GetAcquirerOrderController.getAcquirerOrder(orderId, acquirerSource);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(Map.class);
		verify(acquirerSource).getAcquirerOrder(orderId);
	}

	@Test @DisplayName("Deve lançar exceção quando pedido não é encontrado no adquirente")
	void shouldThrowExceptionWhenOrderNotFoundInAcquirer() {
		// Arrange
		var orderId = 999L;

		when(acquirerSource.getAcquirerOrder(orderId))
				.thenThrow(new OrderNotFoundException("Pedido não encontrado no adquirente"));

		// Act & Assert
		assertThatThrownBy(() -> GetAcquirerOrderController.getAcquirerOrder(orderId, acquirerSource))
				.isInstanceOf(OrderNotFoundException.class)
				.hasMessageContaining("Pedido não encontrado");

		verify(acquirerSource).getAcquirerOrder(orderId);
	}

	@Test @DisplayName("Deve retornar dados quando pedido existe")
	void shouldReturnDataWhenOrderExists() {
		// Arrange
		var orderId = 456L;
		Map<String, Object> order = new HashMap<>();
		order.put("id", orderId);
		order.put("status", "pending");

		when(acquirerSource.getAcquirerOrder(orderId)).thenReturn(order);

		// Act
		var result = GetAcquirerOrderController.getAcquirerOrder(orderId, acquirerSource);

		// Assert
		assertThat(result).isNotNull();
		verify(acquirerSource).getAcquirerOrder(orderId);
	}

	@Test @DisplayName("Deve chamar AcquirerSource com orderId correto")
	void shouldCallAcquirerSourceWithCorrectOrderId() {
		// Arrange
		var orderId = 789L;
		Map<String, Object> order = new HashMap<>();
		order.put("id", orderId);

		when(acquirerSource.getAcquirerOrder(orderId)).thenReturn(order);

		// Act
		GetAcquirerOrderController.getAcquirerOrder(orderId, acquirerSource);

		// Assert
		verify(acquirerSource).getAcquirerOrder(orderId);
	}
}
