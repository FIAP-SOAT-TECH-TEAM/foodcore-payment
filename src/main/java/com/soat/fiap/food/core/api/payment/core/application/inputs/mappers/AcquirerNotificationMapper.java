package com.soat.fiap.food.core.api.payment.core.application.inputs.mappers;

import com.soat.fiap.food.core.api.payment.core.application.inputs.AcquirerNotificationInput;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.request.AcquirerNotificationRequest;

/**
 * Classe utilitária responsável por mapear objetos entre
 * {@link AcquirerNotificationRequest} e {@link AcquirerNotificationInput}.
 */
public class AcquirerNotificationMapper {

	/**
	 * Converte um {@link AcquirerNotificationRequest} recebido via Webhook em um
	 * {@link AcquirerNotificationInput} utilizado pela camada de aplicação.
	 *
	 * @param request
	 *            Notificação recebida do adquirente.
	 * @return Objeto {@link AcquirerNotificationInput} com os dados necessários
	 *         para o use case.
	 */
	public static AcquirerNotificationInput toInput(AcquirerNotificationRequest request) {
		return new AcquirerNotificationInput(request.getId(), request.isLiveMode(), request.getType(),
				request.getDateCreated(), request.getUserId(), request.getApiVersion(), request.getAction(),
				request.getData() != null ? request.getDataId() : null);
	}
}
