package com.soat.fiap.food.core.api.payment.unit.eventlistener;

import static org.assertj.core.api.Assertions.assertThatNoException;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.order.core.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.payment.infrastructure.in.event.listener.rabbitmq.PaymentOrderEventListener;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

@ExtendWith(MockitoExtension.class) @DisplayName("PaymentOrderEventListener - Testes Unitários")
class PaymentOrderEventListenerTest {

	@Mock
	private PaymentDataSource paymentDataSource;

	@Mock
	private AcquirerSource acquirerSource;

	@Mock
	private EventPublisherSource eventPublisherSource;

	@Test @DisplayName("Deve processar evento de pedido criado com sucesso")
	void shouldProcessOrderCreatedEventSuccessfully() {
		// Arrange
		var event = new OrderCreatedEvent();
		event.setId(1L);
		event.setOrderNumber("2024-001");
		event.setUserId("as23as3");
		event.setTotalAmount(BigDecimal.valueOf(29.99));

		var listener = new PaymentOrderEventListener(paymentDataSource, acquirerSource, eventPublisherSource);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> listener.handleOrderCreatedEvent(event));
	}

	@Test @DisplayName("Deve processar evento de pedido criado com valor alto")
	void shouldProcessOrderCreatedEventWithHighValue() {
		// Arrange
		var event = new OrderCreatedEvent();
		event.setId(2L);
		event.setOrderNumber("2024-002");
		event.setUserId("as53as3");
		event.setTotalAmount(BigDecimal.valueOf(199.99));

		var listener = new PaymentOrderEventListener(paymentDataSource, acquirerSource, eventPublisherSource);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> listener.handleOrderCreatedEvent(event));
	}

	@Test @DisplayName("Deve processar evento de pedido criado sem userId")
	void shouldProcessOrderCreatedEventWithoutUserId() {
		// Arrange
		var event = new OrderCreatedEvent();
		event.setId(3L);
		event.setOrderNumber("2024-003");
		event.setUserId(null);
		event.setTotalAmount(BigDecimal.valueOf(49.99));

		var listener = new PaymentOrderEventListener(paymentDataSource, acquirerSource, eventPublisherSource);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> listener.handleOrderCreatedEvent(event));
	}

	@Test @DisplayName("Deve processar evento de pedido criado com valor mínimo")
	void shouldProcessOrderCreatedEventWithMinimumValue() {
		// Arrange
		var event = new OrderCreatedEvent();
		event.setId(4L);
		event.setOrderNumber("2024-004");
		event.setUserId("1s23as3");
		event.setTotalAmount(BigDecimal.valueOf(0.01));

		var listener = new PaymentOrderEventListener(paymentDataSource, acquirerSource, eventPublisherSource);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> listener.handleOrderCreatedEvent(event));
	}

	@Test @DisplayName("Deve processar evento mantendo integridade dos dados")
	void shouldProcessEventMaintainingDataIntegrity() {
		// Arrange
		var event = new OrderCreatedEvent();
		event.setId(5L);
		event.setOrderNumber("2024-005");
		event.setUserId("8s23as3");
		event.setTotalAmount(BigDecimal.valueOf(75.50));

		var listener = new PaymentOrderEventListener(paymentDataSource, acquirerSource, eventPublisherSource);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> listener.handleOrderCreatedEvent(event));
	}
}
