package com.soat.fiap.food.core.api.catalog.infrastructure.in.event.listener.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.product.UpdateProductStockForCanceledItemsController;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.product.UpdateProductStockForCreatedItemsController;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.OrderCanceledEventDto;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.OrderCreatedEventDto;
import com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq.config.RabbitMqQueueConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * Ouvinte de eventos de pedido no módulo de catálogo via RabbitMQ
 */
@Component @Slf4j
public class CatalogOrderEventListener {

	private final CatalogDataSource catalogDataSource;

	public CatalogOrderEventListener(CatalogDataSource catalogDataSource) {
		this.catalogDataSource = catalogDataSource;
	}

	/**
	 * Processa o evento de pedido criado
	 *
	 * @param event
	 *            Evento de pedido criado
	 */
	@RabbitListener(queues = RabbitMqQueueConfig.ORDER_CATALOG_CREATED_QUEUE)
	public void handleOrderCreatedEvent(OrderCreatedEventDto event) {
		log.info("Módulo Catalogo: Notificado de criação de pedido: {}", event.getId());

		UpdateProductStockForCreatedItemsController.updateProductStockForCreatedItems(event, catalogDataSource);

		log.info("Quantidade em estoque atualizada para: {} produtos.", event.getItems().size());
	}

	/**
	 * Processa o evento de pedido cancelado
	 *
	 * @param event
	 *            Evento de pedido cancelado
	 */
	@RabbitListener(queues = RabbitMqQueueConfig.ORDER_CANCELED_QUEUE)
	public void handleOrderCanceledEvent(OrderCanceledEventDto event) {
		log.info("Módulo Catalogo: Notificado de cancelamento de pedido: {}", event.getId());

		UpdateProductStockForCanceledItemsController.updateProductStockForCanceledItems(event, catalogDataSource);

		log.info("Quantidade em estoque atualizada para: {} produtos.", event.getItems().size());
	}
}
