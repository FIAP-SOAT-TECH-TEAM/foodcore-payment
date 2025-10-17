package com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.*;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq.config.RabbitMqQueueConfig;

/**
 * Implementação do {@link EventPublisherSource} usando RabbitMQ.
 * <p>
 * Esta classe envia eventos de domínio para filas RabbitMQ correspondentes.
 * Cada método publica um tipo de evento específico.
 * </p>
 */
@Component
public class RabbitMqEventPublisher implements EventPublisherSource {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishOrderCreatedEvent(OrderCreatedEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqQueueConfig.ORDER_CREATED_FANOUT_EXCHANGE, "", event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishOrderCanceledEvent(OrderCanceledEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqQueueConfig.ORDER_CANCELED_QUEUE, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishProductCreatedEvent(ProductCreatedEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqQueueConfig.PRODUCT_CREATED_QUEUE, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishPaymentApprovedEvent(PaymentApprovedEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqQueueConfig.PAYMENT_APPROVED_QUEUE, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishPaymentExpiredEvent(PaymentExpiredEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqQueueConfig.PAYMENT_EXPIRED_QUEUE, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishPaymentInitializationErrorEvent(PaymentInitializationErrorEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqQueueConfig.PAYMENT_INITIALIZATION_ERROR_QUEUE, event);
	}
}
