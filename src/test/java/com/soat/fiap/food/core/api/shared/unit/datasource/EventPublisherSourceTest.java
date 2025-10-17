package com.soat.fiap.food.core.api.shared.unit.datasource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq.RabbitMqEventPublisher;

@ExtendWith(MockitoExtension.class) @DisplayName("EventPublisherSource - Testes Unitários")
class EventPublisherSourceTest {

	@Mock
	private ApplicationEventPublisher applicationEventPublisher;

	@Test @DisplayName("Deve publicar evento com sucesso")
	void shouldPublishEventSuccessfully() {
		// Arrange
		var eventPublisher = new RabbitMqEventPublisher(applicationEventPublisher);
		var event = new TestEvent("Test Event");

		// Act & Assert
		assertThatNoException().isThrownBy(() -> eventPublisher.publishEvent(event));

		verify(applicationEventPublisher).publishEvent(event);
	}

	@Test @DisplayName("Deve publicar evento string com sucesso")
	void shouldPublishStringEventSuccessfully() {
		// Arrange
		var eventPublisher = new RabbitMqEventPublisher(applicationEventPublisher);
		var event = "String Event";

		// Act & Assert
		assertThatNoException().isThrownBy(() -> eventPublisher.publishEvent(event));

		verify(applicationEventPublisher).publishEvent(event);
	}

	@Test @DisplayName("Deve publicar múltiplos eventos com sucesso")
	void shouldPublishMultipleEventsSuccessfully() {
		// Arrange
		var eventPublisher = new RabbitMqEventPublisher(applicationEventPublisher);
		var event1 = new TestEvent("Event 1");
		var event2 = new TestEvent("Event 2");

		// Act & Assert
		assertThatNoException().isThrownBy(() -> {
			eventPublisher.publishEvent(event1);
			eventPublisher.publishEvent(event2);
		});

		verify(applicationEventPublisher).publishEvent(event1);
		verify(applicationEventPublisher).publishEvent(event2);
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
