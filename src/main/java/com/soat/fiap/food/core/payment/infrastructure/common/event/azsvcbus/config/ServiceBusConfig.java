package com.soat.fiap.food.core.payment.infrastructure.common.event.azsvcbus.config;

import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do Azure Service Bus.
 * <p>
 */
@Configuration
public class ServiceBusConfig {

	/** Nome do tópico para eventos de pedido criado. */
	public static final String ORDER_CREATED_TOPIC = "order.created.topic";

	/** Nome da subscription para eventos de pedido criado. */
	public static final String PAYMENT_ORDER_CREATED_TOPIC_SUBSCRIPTION = "payment.order.created.topic.subscription";

	/** Fila para eventos de pagamento aprovado. */
	public static final String PAYMENT_APPROVED_QUEUE = "payment.approved.queue";

	/** Fila para eventos de pagamento expirado. */
	public static final String PAYMENT_EXPIRED_QUEUE = "payment.expired.queue";

	/** Fila para eventos de erro na inicialização do pagamento. */
	public static final String PAYMENT_INITIALIZATION_ERROR_QUEUE = "payment.initialization.error.queue";
}
