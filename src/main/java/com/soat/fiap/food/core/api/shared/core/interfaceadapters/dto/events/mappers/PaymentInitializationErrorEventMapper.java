package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.mappers;

import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentInitializationErrorEvent;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.PaymentInitializationErrorEventDto;

/**
 * Classe utilitária responsável por mapear
 * {@link PaymentInitializationErrorEvent} para o DTO
 * {@link PaymentInitializationErrorEventDto}, utilizado para transporte de
 * dados do evento de erro na inicialização de pagamento.
 */
public class PaymentInitializationErrorEventMapper {

	/**
	 * Converte um {@link PaymentInitializationErrorEvent} em um
	 * {@link PaymentInitializationErrorEventDto}.
	 *
	 * @param event
	 *            Evento de erro na inicialização de pagamento.
	 * @return DTO com os dados do erro ocorrido.
	 */
	public static PaymentInitializationErrorEventDto toDto(PaymentInitializationErrorEvent event) {
		PaymentInitializationErrorEventDto dto = new PaymentInitializationErrorEventDto();
		dto.setOrderId(event.getOrderId());
		dto.setErrorMessage(event.getErrorMessage());
		return dto;
	}
}
