package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentApprovedEvent;

import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio
 * {@link PaymentApprovedEvent}. Serve como objeto de transferência entre o
 * domínio e o mundo externo (DataSource).
 */
@Data
public class PaymentApprovedEventDto {
	public Long paymentId;
	public Long orderId;
	public BigDecimal amount;
	public String paymentMethod;
	public LocalDateTime approvedAt;
}
