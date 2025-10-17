package com.soat.fiap.food.core.api.payment.core.domain.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Evento de domínio (DDD) emitido quando um pagamento expirou
 */
@Getter @AllArgsConstructor
public class PaymentExpiredEvent {
	private final Long paymentId;
	private final Long orderId;
	private final LocalDateTime expiredIn;
}
