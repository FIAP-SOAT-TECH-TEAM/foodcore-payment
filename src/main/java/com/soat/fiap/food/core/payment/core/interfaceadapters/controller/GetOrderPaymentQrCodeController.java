package com.soat.fiap.food.core.payment.core.interfaceadapters.controller;

import com.soat.fiap.food.core.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.payment.core.interfaceadapters.presenter.PaymentPresenter;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response.QrCodeResponse;
import com.soat.fiap.food.core.shared.core.interfaceadapters.gateways.AccessManagerGateway;
import com.soat.fiap.food.core.shared.infrastructure.common.source.AccessManagerSource;

/**
 * Controller: Obter QR Code de pagamento de um pedido.
 */
public class GetOrderPaymentQrCodeController {

	/**
	 * Obtém o QR Code de pagamento do pedido pelo ID do pedido.
	 *
	 * @param orderId
	 *            ID do pedido.
	 * @param paymentDataSource
	 *            Origem de dados para o gateway de pagamento.
	 * @param accessManagerSource
	 *            Origem de dados para o gateway de autenticação e autorização.
	 * @return Objeto {@link QrCodeResponse} com o QR Code e dados relacionados ao
	 *         pagamento.
	 */
	public static QrCodeResponse getOrderPaymentQrCode(Long orderId, PaymentDataSource paymentDataSource,
			AccessManagerSource accessManagerSource) {

		var paymentGateway = new PaymentGateway(paymentDataSource);
		var accessManagerGateway = new AccessManagerGateway(accessManagerSource);
		var payment = GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId, paymentGateway,
				accessManagerGateway);

		return PaymentPresenter.toQrCodeResponse(payment);
	}
}
