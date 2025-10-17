package com.soat.fiap.food.core.api.payment.infrastructure.in.event.listener.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.controller.web.api.InitializePaymentController;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.OrderCreatedEventDto;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq.config.RabbitMqQueueConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * Ouvinte de eventos de pedido no módulo de pagamentos via RabbitMQ
 */
@Component @Slf4j
public class PaymentOrderEventListener {

	private final PaymentDataSource paymentDataSource;
	private final AcquirerSource acquirerSource;
	private final EventPublisherSource eventPublisherSource;

	/**
	 * Construtor do listener de eventos de pedidos para pagamentos.
	 *
	 * @param paymentDataSource
	 *            Fonte de dados de pagamentos
	 * @param acquirerSource
	 *            Interface para o adquirente de pagamentos
	 * @param eventPublisherSource
	 *            Publicador de eventos
	 */
	public PaymentOrderEventListener(PaymentDataSource paymentDataSource, AcquirerSource acquirerSource,
			EventPublisherSource eventPublisherSource) {
		this.paymentDataSource = paymentDataSource;
		this.acquirerSource = acquirerSource;
		this.eventPublisherSource = eventPublisherSource;
	}

	/**
	 * Processa o evento de pedido criado.
	 * <p>
	 * Inicia o pagamento do pedido e publica eventos relacionados, registrando log
	 * de notificação sobre a operação.
	 * </p>
	 *
	 * @param event
	 *            Evento de pedido criado
	 */
	@RabbitListener(queues = RabbitMqQueueConfig.ORDER_PAYMENT_CREATED_QUEUE)
	public void handleOrderCreatedEvent(OrderCreatedEventDto event) {
		log.info("Módulo Payment: Iniciando pagamento para o pedido: {} com valor total: {}", event.getId(),
				event.getTotalAmount());

		InitializePaymentController.initializePayment(event, paymentDataSource, acquirerSource, eventPublisherSource);
	}
}
