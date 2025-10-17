package com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.presenter.web.api.PaymentPresenter;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.PaymentResponse;

/**
 * Controller: Obter status de pagamento de um pedido.
 */
public class GetLatestOrderPaymentByOrderIdController {

	/**
	 * Obtém o pagamento mais recente de um pedido com base no ID do pedido.
	 *
	 * @param orderId
	 *            ID do pedido.
	 * @param paymentDataSource
	 *            Origem de dados para o gateway de pagamento.
	 * @return Objeto {@link PaymentResponse} contendo todos os dados do pagamento
	 *         mais recente associado ao pedido.
	 */
	public static PaymentResponse getLatestOrderPaymentByOrderId(Long orderId, PaymentDataSource paymentDataSource) {

		var paymentGateway = new PaymentGateway(paymentDataSource);
		var payment = GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId, paymentGateway, null);

		return PaymentPresenter.toPaymentResponse(payment);
	}
}
