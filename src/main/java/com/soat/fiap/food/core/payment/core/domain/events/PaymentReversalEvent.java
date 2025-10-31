package com.soat.fiap.food.core.payment.core.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Evento de domínio (DDD) emitido quando um pagamento é estornado
 */
@Getter @AllArgsConstructor
public class PaymentReversalEvent {
	private final Long orderId;
}
