package com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.presenter.web.api.PaymentPresenter;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.PaymentStatusResponse;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.AccessManagerGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AccessManagerSource;

/**
 * Controller: Obter status de pagamento de um pedido.
 */
public class GetOrderPaymentStatusController {

	/**
	 * Obtém o status de pagamento de um pedido com base no ID do pedido.
	 *
	 * @param orderId
	 *            ID do pedido.
	 * @param paymentDataSource
	 *            Origem de dados para o gateway de pagamento.
	 * @param accessManagerSource
	 *            Origem de dados para o gateway de autenticação e autorização.
	 * @return Objeto {@link PaymentStatusResponse} com o status de pagamento do
	 *         pedido.
	 */
	public static PaymentStatusResponse getOrderPaymentStatus(Long orderId, PaymentDataSource paymentDataSource,
			AccessManagerSource accessManagerSource) {

		var paymentGateway = new PaymentGateway(paymentDataSource);
		var accessManagerGateway = new AccessManagerGateway(accessManagerSource);
		var payment = GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId, paymentGateway,
				accessManagerGateway);

		return PaymentPresenter.toPaymentStatusResponse(payment);
	}
}
