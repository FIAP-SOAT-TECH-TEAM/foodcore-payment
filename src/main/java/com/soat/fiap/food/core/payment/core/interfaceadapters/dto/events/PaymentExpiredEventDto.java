package com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events;

import java.time.LocalDateTime;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentExpiredEvent;

import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio
 * {@link PaymentExpiredEvent}. Serve como objeto de transferência entre o
 * domínio e o mundo externo (DataSource).
 */
@Data
public class PaymentExpiredEventDto {
	public Long paymentId;
	public Long orderId;
	public LocalDateTime expiredIn;
}
