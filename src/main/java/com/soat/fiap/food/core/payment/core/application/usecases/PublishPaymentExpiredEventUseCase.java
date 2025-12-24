package com.soat.fiap.food.core.payment.core.application.usecases;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentExpiredEvent;
import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.EventPublisherGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Publicar evento de pagamento expirado.
 */
@Slf4j
public class PublishPaymentExpiredEventUseCase {

	/**
	 * Publica o evento {@link PaymentExpiredEvent} para o pagamento expirado
	 * fornecido.
	 *
	 * @param expiredPayment
	 *            Pagamento que foi identificado como expirado
	 * @param gateway
	 *            Gateway responsável por publicar o evento
	 */
	public static void publishPaymentExpiredEvent(Payment expiredPayment, EventPublisherGateway gateway) {
		var expiredEvent = new PaymentExpiredEvent(expiredPayment.getId(), expiredPayment.getOrderId(),
				expiredPayment.getExpiresIn());
		gateway.publishPaymentExpiredEvent(expiredEvent);
		log.info("Evento de pagamento expirado publicado: {}", expiredPayment.getId());
	}
}
