package com.soat.fiap.food.core.payment.core.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Evento de domínio emitido (DDD) quando ocorre um erro na inicialização do
 * pagamento
 */
@Getter @AllArgsConstructor
public class PaymentInitializationErrorEvent {
	private final Long orderId;
	private final String errorMessage;
}
