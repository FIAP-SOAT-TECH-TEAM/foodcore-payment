package com.soat.fiap.food.core.api.order.infrastructure.in.event.listener.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.ProductCreatedEventDto;
import com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq.config.RabbitMqQueueConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * Ouvinte de eventos de catálogo no módulo de pedidos via RabbitMQ
 */
@Component @Slf4j
public class OrderProductEventListener {

	/**
	 * Processa o evento de produto criado
	 *
	 * @param event
	 *            Evento de produto criado
	 */
	@RabbitListener(queues = RabbitMqQueueConfig.PRODUCT_CREATED_QUEUE)
	public void handleProductCreatedEvent(ProductCreatedEventDto event) {
		log.info("Módulo Order: Notificado da criação do produto: {} (ID: {}), com preço: {}", event.getProductName(),
				event.getProductId(), event.getPrice());
	}
}
