package com.soat.fiap.food.core.api.shared.infrastructure.common.source;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.*;

/**
 * Interface para publicação de eventos no sistema.
 * <p>
 * Fornece métodos para publicar diferentes tipos de eventos. Implementações
 * dessa interface podem enviar os eventos para mecanismos de mensageria (como
 * RabbitMQ) ou outros sistemas de eventos.
 * </p>
 */
public interface EventPublisherSource {

	/**
	 * Publica um evento de pedido criado.
	 *
	 * @param orderCreatedEventDto
	 *            evento contendo informações do pedido criado.
	 */
	void publishOrderCreatedEvent(OrderCreatedEventDto orderCreatedEventDto);

	/**
	 * Publica um evento de pedido cancelado.
	 *
	 * @param orderCanceledEventDto
	 *            evento contendo informações do pedido cancelado.
	 */
	void publishOrderCanceledEvent(OrderCanceledEventDto orderCanceledEventDto);

	/**
	 * Publica um evento de produto criado.
	 *
	 * @param productCreatedEventDto
	 *            evento contendo informações do produto criado.
	 */
	void publishProductCreatedEvent(ProductCreatedEventDto productCreatedEventDto);

	/**
	 * Publica um evento de pagamento aprovado.
	 *
	 * @param paymentApprovedEventDto
	 *            evento contendo informações do pagamento aprovado.
	 */
	void publishPaymentApprovedEvent(PaymentApprovedEventDto paymentApprovedEventDto);

	/**
	 * Publica um evento de pagamento expirado.
	 *
	 * @param paymentExpiredEventDton
	 *            evento contendo informações do pagamento expirado.
	 */
	void publishPaymentExpiredEvent(PaymentExpiredEventDto paymentExpiredEventDton);

	/**
	 * Publica um evento de erro na inicialização do pagamento.
	 *
	 * @param paymentInitializationErrorEventDto
	 *            evento contendo informações do erro ocorrido durante a
	 *            inicialização do pagamento.
	 */
	void publishPaymentInitializationErrorEvent(PaymentInitializationErrorEventDto paymentInitializationErrorEventDto);

}
