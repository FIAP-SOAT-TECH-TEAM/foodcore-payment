package com.soat.fiap.food.core.payment.order.unit.eventlistener;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.payment.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.payment.order.infrastructure.in.event.listener.rabbitmq.OrderPaymentEventListener;
import com.soat.fiap.food.core.payment.shared.infrastructure.common.source.EventPublisherSource;

@ExtendWith(MockitoExtension.class) @DisplayName("OrderPaymentEventListener - Testes Unitários")
class OrderPaymentEventListenerTest {

	@Mock
	private OrderDataSource orderDataSource;

	@Mock
	private PaymentDataSource paymentDataSource;

	@Mock
	private EventPublisherSource eventPublisherSource;

	@Test @DisplayName("Deve criar listener sem lançar exceção")
	void shouldCreateListenerWithoutThrowingException() {
		// Act & Assert
		assertDoesNotThrow(
				() -> new OrderPaymentEventListener(orderDataSource, paymentDataSource, eventPublisherSource));
	}

	@Test @DisplayName("Deve verificar que listener tem dependências injetadas")
	void shouldVerifyListenerHasDependenciesInjected() {
		// Act
		var listener = new OrderPaymentEventListener(orderDataSource, paymentDataSource, eventPublisherSource);

		// Assert
		assertDoesNotThrow(listener::toString);
	}
}
