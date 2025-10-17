package com.soat.fiap.food.core.payment.core.application.usecases;

import com.soat.fiap.food.core.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.payment.shared.core.interfaceadapters.gateways.AccessManagerGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter o status de pagamento de um pedido.
 */
@Slf4j
public class GetLatestPaymentByOrderIdUseCase {

	/**
	 * Recupera o pagamento mais recente associado ao pedido informado.
	 *
	 * @param orderId
	 *            ID do pedido.
	 * @param paymentGateway
	 *            Gateway responsável por acessar os dados de pagamento.
	 * @param accessManagerGateway
	 *            Gateway responsável por validações de autenticação e autorização.
	 *            Se não informado, nada acontecerá.
	 * @return Instância de {@link Payment} com o status mais recente.
	 * @throws PaymentNotFoundException
	 *             caso nenhum pagamento seja encontrado para o pedido.
	 */
	public static Payment getLatestPaymentByOrderId(Long orderId, PaymentGateway paymentGateway,
			AccessManagerGateway accessManagerGateway) {
		var payment = paymentGateway.findTopByOrderIdOrderByIdDesc(orderId);

		if (payment.isEmpty()) {
			log.warn("Pagamento não foi encontrado a partir do orderId! {}", orderId);
			throw new PaymentNotFoundException("Pagamento", orderId);
		}

		if (accessManagerGateway != null) {
			accessManagerGateway.validateAccess(payment.get().getUserId());
		}

		return payment.get();
	}
}
