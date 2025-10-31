package com.soat.fiap.food.core.payment.core.application.usecases;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentReversalEvent;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.EventPublisherGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Publicar evento de pagamento estornado.
 */
@Slf4j
public class PublishPaymentReversalEventUseCase {

	/**
	 * Publica o evento {@link PaymentReversalEvent} para os pagamentos estornados do pedido fornecido.
	 *
	 * @param orderId
	 *            ID do pedido cujo pagamantos foram estornados
	 * @param gateway
	 *            Gateway responsável por publicar o evento
	 */
	public static void publishPaymentReversalEvent(Long orderId, EventPublisherGateway gateway) {
		var reversalEvent = new PaymentReversalEvent(orderId);
		gateway.publishPaymentReversalEvent(reversalEvent);
		log.info("Evento de pagamento estornado publicado. Pedido: {}", reversalEvent.getOrderId());
	}
}
