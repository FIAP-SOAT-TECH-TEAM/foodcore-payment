package com.soat.fiap.food.core.payment.infrastructure.common.source;

import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.*;

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
	 * Publica um evento de pagamento aprovado.
	 *
	 * @param paymentApprovedEventDto
	 *            evento contendo informações do pagamento aprovado.
	 */
	void publishPaymentApprovedEvent(PaymentApprovedEventDto paymentApprovedEventDto);

	/**
	 * Publica um evento de pagamento expirado.
	 *
	 * @param paymentExpiredEventDto
	 *            evento contendo informações do pagamento expirado.
	 */
	void publishPaymentExpiredEvent(PaymentExpiredEventDto paymentExpiredEventDto);
}
