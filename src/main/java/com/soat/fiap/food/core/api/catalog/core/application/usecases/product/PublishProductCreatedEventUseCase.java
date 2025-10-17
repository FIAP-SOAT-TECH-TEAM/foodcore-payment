package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.core.domain.events.ProductCreatedEvent;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;

/**
 * Caso de uso: publicar o evento {@link ProductCreatedEvent}
 */
public class PublishProductCreatedEventUseCase {

	/**
	 * Publica o evento {@link ProductCreatedEvent}
	 *
	 * @param product
	 *            O produto criado que será convertido em evento.
	 * @param gateway
	 *            O gateway responsável por publicar o evento.
	 */
	public static void publishProductCreatedEvent(Product product, EventPublisherGateway gateway) {
		var event = ProductCreatedEvent.of(product.getId(), product.getName(), product.getPrice(),
				product.getCategoryId());
		gateway.publishProductCreatedEvent(event);
	}
}
