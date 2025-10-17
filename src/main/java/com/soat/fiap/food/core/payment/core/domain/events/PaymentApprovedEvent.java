package com.soat.fiap.food.core.payment.core.domain.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Evento de domínio (DDD) emitido quando um pagamento é aprovado
 */
@Getter @AllArgsConstructor
public class PaymentApprovedEvent {
	private final Long paymentId;
	private final Long orderId;
	private final BigDecimal amount;
	private final String paymentMethod;
	private final LocalDateTime approvedAt;

	/**
	 * Cria um evento de pagamento aprovado
	 *
	 * @param paymentId
	 *            ID do pagamento
	 * @param orderId
	 *            ID do pedido
	 * @param amount
	 *            Valor do pagamento
	 * @param paymentMethod
	 *            Método de pagamento
	 * @return Nova instância do evento
	 */
	public static PaymentApprovedEvent of(Long paymentId, Long orderId, BigDecimal amount, String paymentMethod) {
		return new PaymentApprovedEvent(paymentId, orderId, amount, paymentMethod, LocalDateTime.now());
	}
}
