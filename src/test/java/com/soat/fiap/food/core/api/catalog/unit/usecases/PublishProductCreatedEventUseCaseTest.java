package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.PublishProductCreatedEventUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.events.ProductCreatedEvent;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("PublishProductCreatedEventUseCase - Testes Unitários")
class PublishProductCreatedEventUseCaseTest {

	@Mock
	private EventPublisherGateway eventPublisherGateway;

	@Test @DisplayName("Deve publicar evento de produto criado com sucesso")
	void shouldPublishProductCreatedEventSuccessfully() {
		// Arrange
		var product = CatalogFixture.createValidProduct();

		// Act & Assert
		assertDoesNotThrow(
				() -> PublishProductCreatedEventUseCase.publishProductCreatedEvent(product, eventPublisherGateway));

		verify(eventPublisherGateway).publishEvent(any());
	}

	@Test @DisplayName("Deve chamar o gateway uma única vez")
	void shouldCallGatewayOnlyOnce() {
		// Arrange
		var product = CatalogFixture.createValidProduct();

		// Act
		PublishProductCreatedEventUseCase.publishProductCreatedEvent(product, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway, times(1)).publishEvent(any());
	}

	@Test @DisplayName("Deve processar diferentes produtos")
	void shouldProcessDifferentProducts() {
		// Arrange
		var product1 = CatalogFixture.createValidProduct();
		var product2 = CatalogFixture.createValidProduct();

		// Act
		PublishProductCreatedEventUseCase.publishProductCreatedEvent(product1, eventPublisherGateway);
		PublishProductCreatedEventUseCase.publishProductCreatedEvent(product2, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway, times(2)).publishEvent(any());
	}

	@Test @DisplayName("Deve publicar evento com dados corretos do produto")
	void shouldPublishEventWithCorrectProductData() {
		// Arrange
		var product = CatalogFixture.createValidProduct();
		var eventCaptor = ArgumentCaptor.forClass(ProductCreatedEvent.class);

		// Act
		PublishProductCreatedEventUseCase.publishProductCreatedEvent(product, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());
		var capturedEvent = eventCaptor.getValue();

		assertThat(capturedEvent.getProductId()).isEqualTo(product.getId());
		assertThat(capturedEvent.getProductName()).isEqualTo(product.getName());
		assertThat(capturedEvent.getPrice()).isEqualTo(product.getPrice());
		assertThat(capturedEvent.getCategoryId()).isEqualTo(product.getCategoryId());
	}

	@Test @DisplayName("Deve publicar evento para produto sem imagem")
	void shouldPublishEventForProductWithoutImage() {
		// Arrange
		var product = CatalogFixture.createProductWithoutImage();
		var eventCaptor = ArgumentCaptor.forClass(ProductCreatedEvent.class);

		// Act
		PublishProductCreatedEventUseCase.publishProductCreatedEvent(product, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());
		var capturedEvent = eventCaptor.getValue();

		assertThat(capturedEvent.getProductId()).isEqualTo(product.getId());
		assertThat(capturedEvent.getProductName()).isEqualTo(product.getName());
	}

	@Test @DisplayName("Deve garantir que timestamp do evento seja criado automaticamente")
	void shouldEnsureEventTimestampIsCreatedAutomatically() {
		// Arrange
		var product = CatalogFixture.createValidProduct();
		var eventCaptor = ArgumentCaptor.forClass(ProductCreatedEvent.class);

		// Act
		PublishProductCreatedEventUseCase.publishProductCreatedEvent(product, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());
		var capturedEvent = eventCaptor.getValue();

		assertThat(capturedEvent.getTimestamp()).isNotNull();
		assertThat(capturedEvent.getTimestamp()).isBeforeOrEqualTo(java.time.LocalDateTime.now());
	}

	@Test @DisplayName("Deve publicar evento mantendo integridade dos dados do produto")
	void shouldPublishEventMaintainingProductDataIntegrity() {
		// Arrange
		var product = CatalogFixture.createValidProduct();
		var originalId = product.getId();
		var originalName = product.getName();
		var originalPrice = product.getPrice();
		var originalCategoryId = product.getCategoryId();

		var eventCaptor = ArgumentCaptor.forClass(ProductCreatedEvent.class);

		// Act
		PublishProductCreatedEventUseCase.publishProductCreatedEvent(product, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());
		var capturedEvent = eventCaptor.getValue();

		assertThat(capturedEvent.getProductId()).isEqualTo(originalId);
		assertThat(capturedEvent.getProductName()).isEqualTo(originalName);
		assertThat(capturedEvent.getPrice()).isEqualTo(originalPrice);
		assertThat(capturedEvent.getCategoryId()).isEqualTo(originalCategoryId);
	}

	@Test @DisplayName("Deve criar evento com timestamp atual")
	void shouldCreateEventWithCurrentTimestamp() {
		// Arrange
		var product = CatalogFixture.createValidProduct();
		var eventCaptor = ArgumentCaptor.forClass(ProductCreatedEvent.class);
		var beforeEvent = java.time.LocalDateTime.now();

		// Act
		PublishProductCreatedEventUseCase.publishProductCreatedEvent(product, eventPublisherGateway);

		// Assert
		verify(eventPublisherGateway).publishEvent(eventCaptor.capture());
		var capturedEvent = eventCaptor.getValue();
		var afterEvent = java.time.LocalDateTime.now();

		assertThat(capturedEvent.getTimestamp()).isAfterOrEqualTo(beforeEvent);
		assertThat(capturedEvent.getTimestamp()).isBeforeOrEqualTo(afterEvent);
	}
}
