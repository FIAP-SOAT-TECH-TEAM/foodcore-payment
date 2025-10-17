package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.mappers;

import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentExpiredEvent;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.PaymentExpiredEventDto;

/**
 * Classe utilitária responsável por mapear {@link PaymentExpiredEvent} para o
 * DTO {@link PaymentExpiredEventDto}, utilizado para transporte de dados do
 * evento de pagamento expirado.
 */
public class PaymentExpiredEventMapper {

	/**
	 * Converte um {@link PaymentExpiredEvent} em um {@link PaymentExpiredEventDto}.
	 *
	 * @param event
	 *            Evento de pagamento expirado.
	 * @return DTO com os dados do pagamento expirado.
	 */
	public static PaymentExpiredEventDto toDto(PaymentExpiredEvent event) {
		PaymentExpiredEventDto dto = new PaymentExpiredEventDto();
		dto.setPaymentId(event.getPaymentId());
		dto.setOrderId(event.getOrderId());
		dto.setExpiredIn(event.getExpiredIn());
		return dto;
	}
}
