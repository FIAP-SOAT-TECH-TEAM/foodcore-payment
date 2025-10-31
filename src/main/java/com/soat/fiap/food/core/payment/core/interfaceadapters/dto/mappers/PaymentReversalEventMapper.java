package com.soat.fiap.food.core.payment.core.interfaceadapters.dto.mappers;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentReversalEvent;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.PaymentReversalEventDto;

/**
 * Classe utilitária responsável por mapear {@link PaymentReversalEvent} para o
 * DTO {@link PaymentReversalEventDto}, utilizado para transporte de dados do
 * evento de pagamento estornado.
 */
public class PaymentReversalEventMapper {

	/**
	 * Converte um {@link PaymentReversalEvent} em um {@link PaymentReversalEventDto}.
	 *
	 * @param event
	 *            Evento de pagamento estornado.
	 * @return DTO com os dados do pagamento estornado.
	 */
	public static PaymentReversalEventDto toDto(PaymentReversalEvent event) {
		return new PaymentReversalEventDto(event.getOrderId());
	}
}
