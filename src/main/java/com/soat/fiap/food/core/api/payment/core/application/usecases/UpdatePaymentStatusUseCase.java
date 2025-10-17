package com.soat.fiap.food.core.api.payment.core.application.usecases;

import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

/**
 * Caso de uso: Atualizar o status de um pagamento.
 */
public class UpdatePaymentStatusUseCase {

	/**
	 * Atualiza o status de um pagamento para o valor informado.
	 *
	 * @param payment
	 *            Pagamento que será atualizado
	 * @param paymentStatus
	 *            Novo status a ser atribuído
	 * @return Pagamento com status atualizado
	 */
	public static Payment updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
		payment.setStatus(paymentStatus);
		return payment;
	}
}
