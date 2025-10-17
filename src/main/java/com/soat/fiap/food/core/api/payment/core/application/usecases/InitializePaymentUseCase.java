package com.soat.fiap.food.core.api.payment.core.application.usecases;

import com.soat.fiap.food.core.api.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Inicializar pagamento.
 */
@Slf4j
public class InitializePaymentUseCase {

	/**
	 * Inicializa o pagamento para um pedido, gerando um QR Code via adquirente.
	 * Caso já exista pagamento para o pedido, retorna o existente.
	 *
	 * @param orderCreatedInput
	 *            dados do pedido criado
	 * @param paymentGateway
	 *            gateway para persistência e consulta de pagamentos
	 * @param acquirerGateway
	 *            gateway para comunicação com o adquirente
	 * @return Pagamento inicializado ou existente
	 */
	public static Payment initializePayment(OrderCreatedInput orderCreatedInput, PaymentGateway paymentGateway,
			AcquirerGateway acquirerGateway) {
		var existingPayment = paymentGateway.findTopByOrderIdOrderByIdDesc(orderCreatedInput.orderId());

		if (existingPayment.isPresent()) {
			log.info("Pagamento já inicializado para o pedido {}", orderCreatedInput.orderId());
			return existingPayment.get();
		}

		var payment = new Payment(orderCreatedInput.userId(), orderCreatedInput.orderId(),
				orderCreatedInput.totalAmount());

		var qrCode = acquirerGateway.generateQrCode(orderCreatedInput, payment.getExpiresIn());

		payment.setQrCode(qrCode);

		return payment;
	}
}
