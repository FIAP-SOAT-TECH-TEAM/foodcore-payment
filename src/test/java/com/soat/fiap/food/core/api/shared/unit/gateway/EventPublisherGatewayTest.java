package com.soat.fiap.food.core.api.shared.unit.gateway;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

@ExtendWith(MockitoExtension.class) @DisplayName("EventPublisherGateway - Testes Unitários")
class EventPublisherGatewayTest {

	@Mock
	private EventPublisherSource eventPublisherSource;

	@Test @DisplayName("Deve publicar evento através do gateway com sucesso")
	void shouldPublishEventThroughGatewaySuccessfully() {
		// Arrange
		var gateway = new EventPublisherGateway(eventPublisherSource);
		var event = new TestEvent("Test Event");

		// Act & Assert
		assertThatNoException().isThrownBy(() -> gateway.publishEvent(event));

		verify(eventPublisherSource).publishEvent(event);
	}

	@Test @DisplayName("Deve publicar evento string através do gateway")
	void shouldPublishStringEventThroughGateway() {
		// Arrange
		var gateway = new EventPublisherGateway(eventPublisherSource);
		var event = "String Event";

		// Act & Assert
		assertThatNoException().isThrownBy(() -> gateway.publishEvent(event));

		verify(eventPublisherSource).publishEvent(event);
	}

	@Test @DisplayName("Deve publicar evento nulo através do gateway")
	void shouldPublishNullEventThroughGateway() {
		// Arrange
		var gateway = new EventPublisherGateway(eventPublisherSource);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> gateway.publishEvent(null));

		verify(eventPublisherSource).publishEvent(null);
	}

	@Test @DisplayName("Deve delegar corretamente para o eventPublisherSource")
	void shouldDelegateCorrectlyToEventPublisherSource() {
		// Arrange
		var gateway = new EventPublisherGateway(eventPublisherSource);
		var event1 = new TestEvent("Event 1");
		var event2 = new TestEvent("Event 2");

		// Act
		gateway.publishEvent(event1);
		gateway.publishEvent(event2);

		// Assert
		verify(eventPublisherSource).publishEvent(event1);
		verify(eventPublisherSource).publishEvent(event2);
	}

	@Test @DisplayName("Deve publicar eventos de diferentes tipos")
	void shouldPublishEventsOfDifferentTypes() {
		// Arrange
		var gateway = new EventPublisherGateway(eventPublisherSource);
		var stringEvent = "String Event";
		var objectEvent = new TestEvent("Object Event");
		var numberEvent = 123;

		// Act & Assert
		assertThatNoException().isThrownBy(() -> {
			gateway.publishEvent(stringEvent);
			gateway.publishEvent(objectEvent);
			gateway.publishEvent(numberEvent);
		});

		verify(eventPublisherSource).publishEvent(stringEvent);
		verify(eventPublisherSource).publishEvent(objectEvent);
		verify(eventPublisherSource).publishEvent(numberEvent);
	}

	// Classe auxiliar para testes
	private static class TestEvent {
		private final String message;

		public TestEvent(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
