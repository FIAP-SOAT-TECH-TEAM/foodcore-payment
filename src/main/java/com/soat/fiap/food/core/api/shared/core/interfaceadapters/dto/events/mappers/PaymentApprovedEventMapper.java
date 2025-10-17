package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.mappers;

import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.PaymentApprovedEventDto;

/**
 * Classe utilitária responsável por mapear {@link PaymentApprovedEvent} para o
 * DTO {@link PaymentApprovedEventDto}, utilizado para transporte de dados do
 * evento de pagamento aprovado.
 */
public class PaymentApprovedEventMapper {

	/**
	 * Converte um {@link PaymentApprovedEvent} em um
	 * {@link PaymentApprovedEventDto}.
	 *
	 * @param event
	 *            Evento de pagamento aprovado.
	 * @return DTO com os dados do pagamento aprovado.
	 */
	public static PaymentApprovedEventDto toDto(PaymentApprovedEvent event) {
		PaymentApprovedEventDto dto = new PaymentApprovedEventDto();
		dto.setPaymentId(event.getPaymentId());
		dto.setOrderId(event.getOrderId());
		dto.setAmount(event.getAmount());
		dto.setPaymentMethod(event.getPaymentMethod());
		dto.setApprovedAt(event.getApprovedAt());
		return dto;
	}
}
