package com.soat.fiap.food.core.payment.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.UUID;

import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.EventPublisherGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.payment.core.application.usecases.PublishPaymentApprovedEventUseCase;
import com.soat.fiap.food.core.payment.core.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.payment.unit.fixtures.PaymentFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("PublishPaymentApprovedEventUseCase - Testes Unitários")
class PublishPaymentApprovedEventUseCaseTest {

	@Mock
	private EventPublisherGateway eventPublisherGateway;

	@Test @DisplayName("Deve publicar evento de pagamento aprovado com sucesso")
	void shouldPublishPaymentApprovedEventSuccessfully() {
		// Arrange
		var payment = PaymentFixture.createApprovedPayment();

		// Act & Assert
		assertDoesNotThrow(
				() -> PublishPaymentApprovedEventUseCase.publishPaymentApprovedEvent(payment, eventPublisherGateway));

		verify(eventPublisherGateway).publishPaymentApprovedEvent(any());
	}

	@Test @DisplayName("Deve chamar o gateway uma única vez")
	void shouldCallGatewayOnlyOnce() {
		// Arrange
		var payment = PaymentFixture.createApprovedPayment();

		// Act
		PublishPaymentApprovedEventUseCase.publishPaymentApprovedEvent(payment, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway, times(1)).publishPaymentApprovedEvent(any());
	}

	@Test @DisplayName("Deve processar diferentes pagamentos")
	void shouldProcessDifferentPayments() {
		// Arrange
		var payment1 = PaymentFixture.createApprovedPayment();
		var payment2 = PaymentFixture.createApprovedPayment();
		payment2.setId(UUID.randomUUID());

		// Act
		PublishPaymentApprovedEventUseCase.publishPaymentApprovedEvent(payment1, eventPublisherGateway);
		PublishPaymentApprovedEventUseCase.publishPaymentApprovedEvent(payment2, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway, times(2)).publishPaymentApprovedEvent(any());
	}

	@Test @DisplayName("Deve publicar evento com dados completos do pagamento")
	void shouldPublishEventWithCompletePaymentData() {
		// Arrange
		var payment = PaymentFixture.createApprovedPayment();
		payment.setId(UUID.randomUUID());
		payment.setOrderId(200L);
		payment.setAmount(new BigDecimal("75.50"));

		var eventCaptor = ArgumentCaptor.forClass(PaymentApprovedEvent.class);

		// Act
		PublishPaymentApprovedEventUseCase.publishPaymentApprovedEvent(payment, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishPaymentApprovedEvent(eventCaptor.capture());

		var publishedEvent = eventCaptor.getValue();
		assertThat(publishedEvent.getPaymentId()).isEqualTo(payment.getId());
		assertThat(publishedEvent.getOrderId()).isEqualTo(payment.getOrderId());
		assertThat(publishedEvent.getAmount()).isEqualTo(payment.getAmount());
		assertThat(publishedEvent.getPaymentMethod()).isEqualTo(payment.getTypeName());
	}

	@Test @DisplayName("Deve publicar evento para pagamento com valor alto")
	void shouldPublishEventForHighValuePayment() {
		// Arrange
		var payment = PaymentFixture.createApprovedPayment();
		var paymentId = UUID.randomUUID();
		payment.setId(paymentId);
		payment.setOrderId(300L);
		payment.setAmount(new BigDecimal("999.99"));

		var eventCaptor = ArgumentCaptor.forClass(PaymentApprovedEvent.class);

		// Act
		PublishPaymentApprovedEventUseCase.publishPaymentApprovedEvent(payment, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishPaymentApprovedEvent(eventCaptor.capture());

		var publishedEvent = eventCaptor.getValue();
		assertThat(publishedEvent).isNotNull();
		assertThat(publishedEvent.getAmount()).isEqualTo(new BigDecimal("999.99"));
		assertThat(publishedEvent.getPaymentId()).isEqualTo(paymentId);
		assertThat(publishedEvent.getOrderId()).isEqualTo(300L);
	}
}
