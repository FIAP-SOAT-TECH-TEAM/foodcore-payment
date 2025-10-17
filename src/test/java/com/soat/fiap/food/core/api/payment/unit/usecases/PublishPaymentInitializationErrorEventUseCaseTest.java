package com.soat.fiap.food.core.api.payment.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.payment.core.application.usecases.PublishPaymentInitializationErrorEventUseCase;
import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentInitializationErrorEvent;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("PublishPaymentInitializationErrorEventUseCase - Testes Unitários")
class PublishPaymentInitializationErrorEventUseCaseTest {

	@Mock
	private EventPublisherGateway eventPublisherGateway;

	@Test @DisplayName("Deve publicar evento de erro na inicialização do pagamento com sucesso")
	void shouldPublishPaymentInitializationErrorEventSuccessfully() {
		// Arrange
		var orderId = 100L;
		var errorMessage = "Erro ao conectar com o gateway de pagamento";

		var eventCaptor = ArgumentCaptor.forClass(PaymentInitializationErrorEvent.class);

		// Act
		PublishPaymentInitializationErrorEventUseCase.publishPaymentInitializationErrorEvent(orderId, errorMessage,
				eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());

		var publishedEvent = eventCaptor.getValue();
		assertThat(publishedEvent).isNotNull();
		assertThat(publishedEvent.getOrderId()).isEqualTo(100L);
		assertThat(publishedEvent.getErrorMessage()).isEqualTo("Erro ao conectar com o gateway de pagamento");
	}

	@Test @DisplayName("Deve publicar evento com mensagem de erro detalhada")
	void shouldPublishEventWithDetailedErrorMessage() {
		// Arrange
		var orderId = 200L;
		var errorMessage = "Timeout na comunicação com o provedor de pagamento PIX após 30 segundos";

		var eventCaptor = ArgumentCaptor.forClass(PaymentInitializationErrorEvent.class);

		// Act
		PublishPaymentInitializationErrorEventUseCase.publishPaymentInitializationErrorEvent(orderId, errorMessage,
				eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());

		var publishedEvent = eventCaptor.getValue();
		assertThat(publishedEvent.getOrderId()).isEqualTo(orderId);
		assertThat(publishedEvent.getErrorMessage()).isEqualTo(errorMessage);
		assertThat(publishedEvent.getErrorMessage()).contains("Timeout");
		assertThat(publishedEvent.getErrorMessage()).contains("PIX");
	}

	@Test @DisplayName("Deve publicar evento para erro genérico de inicialização")
	void shouldPublishEventForGenericInitializationError() {
		// Arrange
		var orderId = 300L;
		var errorMessage = "Erro interno do sistema";

		var eventCaptor = ArgumentCaptor.forClass(PaymentInitializationErrorEvent.class);

		// Act
		PublishPaymentInitializationErrorEventUseCase.publishPaymentInitializationErrorEvent(orderId, errorMessage,
				eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());

		var publishedEvent = eventCaptor.getValue();
		assertThat(publishedEvent).isNotNull();
		assertThat(publishedEvent.getOrderId()).isEqualTo(300L);
		assertThat(publishedEvent.getErrorMessage()).isEqualTo("Erro interno do sistema");
	}

	@Test @DisplayName("Deve publicar evento com mensagem de erro vazia")
	void shouldPublishEventWithEmptyErrorMessage() {
		// Arrange
		var orderId = 400L;
		var errorMessage = "";

		var eventCaptor = ArgumentCaptor.forClass(PaymentInitializationErrorEvent.class);

		// Act
		PublishPaymentInitializationErrorEventUseCase.publishPaymentInitializationErrorEvent(orderId, errorMessage,
				eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());

		var publishedEvent = eventCaptor.getValue();
		assertThat(publishedEvent).isNotNull();
		assertThat(publishedEvent.getOrderId()).isEqualTo(400L);
		assertThat(publishedEvent.getErrorMessage()).isEqualTo("");
	}
}
