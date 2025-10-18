package com.soat.fiap.food.core.payment.infrastructure.out.event.publisher.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.*;
import com.soat.fiap.food.core.payment.infrastructure.common.event.rabbitmq.config.RabbitMqQueueConfig;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;

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
