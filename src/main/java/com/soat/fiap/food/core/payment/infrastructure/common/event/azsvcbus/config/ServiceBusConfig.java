package com.soat.fiap.food.core.payment.infrastructure.common.event.azsvcbus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

/**
 * Classe de configuração do Azure Service Bus.
 * <p>
 */
@Configuration
public class ServiceBusConfig {

	@Value("${azsvcbus.connection-string}")
	private String connectionString;

	/** Fila para eventos de pagamento aprovado. */
	public static final String PAYMENT_APPROVED_QUEUE = "payment.approved.queue";

	/** Fila para eventos de pagamento expirado. */
	public static final String PAYMENT_EXPIRED_QUEUE = "payment.expired.queue";

	/** Nome do tópico para eventos de estoque debitado. */
	public static final String STOCK_DEBIT_QUEUE = "stock.debit.queue";

	/** Nome do tópico para eventos de pedido cancelado. */
	public static final String ORDER_CANCELED_TOPIC = "order.canceled.topic";

	/** Nome da subscription para eventos de pedido cancelado. */
	public static final String PAYMENT_ORDER_CANCELED_TOPIC_SUBSCRIPTION = "payment.order.canceled.topic.subscription";

	@Bean
	public ServiceBusClientBuilder serviceBusClientBuilder() {
		return new ServiceBusClientBuilder().connectionString(connectionString);
	}

	@Bean
	public ServiceBusSenderClient paymentApprovedSender(ServiceBusClientBuilder builder) {
		return builder.sender().topicName(PAYMENT_APPROVED_QUEUE).buildClient();
	}

	@Bean
	public ServiceBusSenderClient paymentExpiredSender(ServiceBusClientBuilder builder) {
		return builder.sender().topicName(PAYMENT_EXPIRED_QUEUE).buildClient();
	}
}
