package com.soat.fiap.food.core.api.payment.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.payment.core.application.usecases.GetAcquirerOrderUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("GetAcquirerOrderUseCase - Testes Unitários")
class GetAcquirerOrderUseCaseTest {

	@Mock
	private AcquirerGateway acquirerGateway;

	@Test @DisplayName("Deve retornar ordem do adquirente quando encontrada")
	void shouldReturnAcquirerOrderWhenFound() {
		// Arrange
		var orderId = 1L;
		var orderData = Map.of("id", "ACQ123456", "status", "approved", "total_amount", 25.90);

		when(acquirerGateway.getAcquirerOrder(orderId)).thenReturn(orderData);

		// Act
		var result = GetAcquirerOrderUseCase.getAcquirerOrder(orderId, acquirerGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(orderData);
		verify(acquirerGateway).getAcquirerOrder(orderId);
	}

	@Test @DisplayName("Deve lançar exceção quando ordem não encontrada")
	void shouldThrowExceptionWhenOrderNotFound() {
		// Arrange
		var orderId = 999L;

		when(acquirerGateway.getAcquirerOrder(orderId)).thenReturn(null);

		// Act & Assert
		assertThatThrownBy(() -> GetAcquirerOrderUseCase.getAcquirerOrder(orderId, acquirerGateway))
				.isInstanceOf(OrderNotFoundException.class);

		verify(acquirerGateway).getAcquirerOrder(orderId);
	}

	@Test @DisplayName("Deve executar consulta com gateway correto")
	void shouldExecuteQueryWithCorrectGateway() {
		// Arrange
		var orderId = 5L;
		var orderData = Map.of("merchant_order_id", orderId);

		when(acquirerGateway.getAcquirerOrder(orderId)).thenReturn(orderData);

		// Act
		var result = GetAcquirerOrderUseCase.getAcquirerOrder(orderId, acquirerGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(Map.class);
		verify(acquirerGateway).getAcquirerOrder(orderId);
	}
}
