package com.soat.fiap.food.core.payment.core.application.usecases;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.shared.core.interfaceadapters.gateways.EventPublisherGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Publicar evento de pagamento aprovado.
 */
@Slf4j
public class PublishPaymentApprovedEventUseCase {

	/**
	 * Publica o evento {@link PaymentApprovedEvent} com os dados do pagamento
	 * aprovado.
	 *
	 * @param payment
	 *            Pagamento aprovado
	 * @param gateway
	 *            Gateway responsável por publicar o evento
	 */
	public static void publishPaymentApprovedEvent(Payment payment, EventPublisherGateway gateway) {
		log.info("Pagamento aprovado: {}, Publicando evento!", payment.getId());

		gateway.publishPaymentApprovedEvent(PaymentApprovedEvent.of(payment.getId(), payment.getOrderId(),
				payment.getAmount(), payment.getTypeName()));
	}
}
