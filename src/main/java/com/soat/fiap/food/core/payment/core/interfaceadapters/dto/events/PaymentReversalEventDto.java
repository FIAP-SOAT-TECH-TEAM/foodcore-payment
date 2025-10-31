package com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentReversalEvent;
import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio
 * {@link PaymentReversalEvent}. Serve como objeto de transferência entre o
 * domínio e o mundo externo (DataSource).
 */
@Data
public class PaymentReversalEventDto {
	private final Long orderId;
}
