package com.soat.fiap.food.core.payment.core.application.usecases;

import com.soat.fiap.food.core.payment.core.application.inputs.StockDebitInput;
import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;

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
	 * @param stockDebitInput
	 *            dados do débito de estoque
	 * @param paymentGateway
	 *            gateway para persistência e consulta de pagamentos
	 * @param acquirerGateway
	 *            gateway para comunicação com o adquirente
	 * @return Pagamento inicializado ou existente
	 */
	public static Payment initializePayment(StockDebitInput stockDebitInput, PaymentGateway paymentGateway,
			AcquirerGateway acquirerGateway) {
		var existingPayment = paymentGateway.findTopByOrderIdOrderByIdDesc(stockDebitInput.orderId());

		if (existingPayment.isPresent()) {
			log.info("Pagamento já inicializado para o pedido {}", stockDebitInput.orderId());
			return existingPayment.get();
		}

		var payment = new Payment(stockDebitInput.userId(), stockDebitInput.orderId(), stockDebitInput.totalAmount());

		var qrCode = acquirerGateway.generateQrCode(stockDebitInput, payment.getExpiresIn());

		payment.setQrCode(qrCode);

		return payment;
	}
}
