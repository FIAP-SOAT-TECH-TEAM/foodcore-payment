package com.soat.fiap.food.core.payment.infrastructure.common.event.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do RabbitMQ para o sistema.
 * <p>
 * Define as filas e exchanges utilizadas pela aplicação e fornece os beans
 * necessários para integração com o RabbitMQ.
 * </p>
 */
@Configuration
public class RabbitMqQueueConfig {

	/** Fila para eventos de pagamento aprovado. */
	public static final String PAYMENT_APPROVED_QUEUE = "payment.approved.queue";

	/** Fila para eventos de pagamento expirado. */
	public static final String PAYMENT_EXPIRED_QUEUE = "payment.expired.queue";

	/** Fila para eventos de erro na inicialização do pagamento. */
	public static final String PAYMENT_INITIALIZATION_ERROR_QUEUE = "payment.initialization.error.queue";

	/** Fila para eventos de pedido criado, no módulo de pagamento. */
	public static final String ORDER_PAYMENT_CREATED_QUEUE = "order.payment.created.queue";

	/**
	 * Declara a fila de pagamentos aprovados no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de pagamento
	 *         aprovado.
	 */
	@Bean
	public Queue paymentApprovedQueue() {
		return new Queue(PAYMENT_APPROVED_QUEUE, true);
	}

	/**
	 * Declara a fila de pagamentos expirados no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de pagamento
	 *         expirado.
	 */
	@Bean
	public Queue paymentExpiredQueue() {
		return new Queue(PAYMENT_EXPIRED_QUEUE, true);
	}

	/**
	 * Declara a fila de erros na inicialização de pagamento no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de erro na
	 *         inicialização de pagamento.
	 */
	@Bean
	public Queue paymentInitializationErrorQueue() {
		return new Queue(PAYMENT_INITIALIZATION_ERROR_QUEUE, true);
	}
}
