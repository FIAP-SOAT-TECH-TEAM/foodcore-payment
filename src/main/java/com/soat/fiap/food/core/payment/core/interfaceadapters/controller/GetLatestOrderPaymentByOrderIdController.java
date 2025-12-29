package com.soat.fiap.food.core.payment.core.interfaceadapters.controller;

import com.soat.fiap.food.core.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.payment.core.interfaceadapters.presenter.PaymentPresenter;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response.PaymentResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Obter status de pagamento de um pedido.
 */
@Slf4j
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
		log.info("Buscando o pagamento mais recente do pedido: {}", orderId);

		var paymentGateway = new PaymentGateway(paymentDataSource);
		var payment = GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId, paymentGateway, null);

		return PaymentPresenter.toPaymentResponse(payment);
	}
}
