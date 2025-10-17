package com.soat.fiap.food.core.api.payment.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.payment.core.application.usecases.PublishPaymentExpiredEventUseCase;
import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentExpiredEvent;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.shared.fixtures.PaymentFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("PublishPaymentExpiredEventUseCase - Testes Unitários")
class PublishPaymentExpiredEventUseCaseTest {

	@Mock
	private EventPublisherGateway eventPublisherGateway;

	@Test @DisplayName("Deve publicar evento de pagamento expirado com sucesso")
	void shouldPublishPaymentExpiredEventSuccessfully() {
		// Arrange
		var payment = PaymentFixture.createExpiredPayment();
		payment.setId(1L);
		payment.setOrderId(100L);

		var eventCaptor = ArgumentCaptor.forClass(PaymentExpiredEvent.class);

		// Act
		PublishPaymentExpiredEventUseCase.publishPaymentExpiredEvent(payment, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());

		var publishedEvent = eventCaptor.getValue();
		assertThat(publishedEvent).isNotNull();
		assertThat(publishedEvent.getPaymentId()).isEqualTo(1L);
		assertThat(publishedEvent.getOrderId()).isEqualTo(100L);
		assertThat(publishedEvent.getExpiredIn()).isEqualTo(payment.getExpiresIn());
	}

	@Test @DisplayName("Deve publicar evento com dados completos do pagamento expirado")
	void shouldPublishEventWithCompleteExpiredPaymentData() {
		// Arrange
		var payment = PaymentFixture.createExpiredPayment();
		payment.setId(2L);
		payment.setOrderId(200L);

		var eventCaptor = ArgumentCaptor.forClass(PaymentExpiredEvent.class);

		// Act
		PublishPaymentExpiredEventUseCase.publishPaymentExpiredEvent(payment, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());

		var publishedEvent = eventCaptor.getValue();
		assertThat(publishedEvent.getPaymentId()).isEqualTo(payment.getId());
		assertThat(publishedEvent.getOrderId()).isEqualTo(payment.getOrderId());
		assertThat(publishedEvent.getExpiredIn()).isEqualTo(payment.getExpiresIn());
	}

	@Test @DisplayName("Deve publicar evento para pagamento com tempo de expiração passado")
	void shouldPublishEventForPaymentWithPastExpirationTime() {
		// Arrange
		var payment = PaymentFixture.createExpiredPayment();
		payment.setId(3L);
		payment.setOrderId(300L);

		var eventCaptor = ArgumentCaptor.forClass(PaymentExpiredEvent.class);

		// Act
		PublishPaymentExpiredEventUseCase.publishPaymentExpiredEvent(payment, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());

		var publishedEvent = eventCaptor.getValue();
		assertThat(publishedEvent).isNotNull();
		assertThat(publishedEvent.getPaymentId()).isEqualTo(3L);
		assertThat(publishedEvent.getOrderId()).isEqualTo(300L);
		assertThat(publishedEvent.getExpiredIn()).isEqualTo(payment.getExpiresIn());
	}
}
