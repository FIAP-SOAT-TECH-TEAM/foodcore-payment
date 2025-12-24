package com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentExpiredEvent;

import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio
 * {@link PaymentExpiredEvent}. Serve como objeto de transferência entre o
 * domínio e o mundo externo (DataSource).
 */
@Data
public class PaymentExpiredEventDto {
	public UUID paymentId;
	public Long orderId;
	public LocalDateTime expiredIn;
}
