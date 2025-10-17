package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events;

import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentInitializationErrorEvent;

import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio
 * {@link PaymentInitializationErrorEvent}. Serve como objeto de transferência
 * entre o domínio e o mundo externo (DataSource).
 */
@Data
public class PaymentInitializationErrorEventDto {
	public Long orderId;
	public String errorMessage;
}
