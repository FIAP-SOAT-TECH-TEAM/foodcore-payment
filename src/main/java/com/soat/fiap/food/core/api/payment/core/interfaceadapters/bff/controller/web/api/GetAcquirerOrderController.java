package com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetAcquirerOrderUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;

/**
 * Controller: Obter os detalhes de um pedido no adquirente.
 */
public class GetAcquirerOrderController {

	/**
	 * Recupera os detalhes de um pedido com base no ID do pedido do adquirente.
	 *
	 * @param orderId
	 *            ID do pedido no adquirente.
	 * @param acquirerSource
	 *            gateway para comunicação com o adquirente
	 * @return Objeto {@link Object} bruto com os dados do pedido. Obs: não utiliza
	 *         um presenter pois o objetivo é retornar ipsis litteris a resposta do
	 *         adquirente
	 */
	public static Object getAcquirerOrder(Long orderId, AcquirerSource acquirerSource) {

		var acquirerGateway = new AcquirerGateway(acquirerSource);

		return GetAcquirerOrderUseCase.getAcquirerOrder(orderId, acquirerGateway);
	}
}
