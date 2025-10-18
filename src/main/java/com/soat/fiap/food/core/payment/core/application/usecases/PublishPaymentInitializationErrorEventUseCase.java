package com.soat.fiap.food.core.payment.core.application.usecases;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentInitializationErrorEvent;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.EventPublisherGateway;

/**
 * Caso de uso: publicar evento de erro na inicialização do pagamento.
 */
public class PublishPaymentInitializationErrorEventUseCase {

	/**
	 * Publica o evento {@link PaymentInitializationErrorEvent} com os detalhes do
	 * erro ocorrido na inicialização do pagamento.
	 *
	 * @param orderId
	 *            Identificador do pedido relacionado ao erro
	 * @param errorMessage
	 *            Mensagem detalhando a causa do erro
	 * @param gateway
	 *            Gateway responsável por publicar o evento
	 */
	public static void publishPaymentInitializationErrorEvent(Long orderId, String errorMessage,
			EventPublisherGateway gateway) {
		var paymentInitializationErrorEvent = new PaymentInitializationErrorEvent(orderId, errorMessage);
		gateway.publishPaymentInitializationErrorEvent(paymentInitializationErrorEvent);
	}
}
