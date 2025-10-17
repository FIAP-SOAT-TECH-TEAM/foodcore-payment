package com.soat.fiap.food.core.api.payment.core.application.usecases;

import java.time.LocalDateTime;
import java.util.List;

import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Buscar pagamentos expirados que ainda não foram aprovados ou
 * cancelados.
 */
@Slf4j
public class GetExpiredPaymentsUseCase {

	/**
	 * Recupera todos os pagamentos que estão expirados e que ainda não possuem
	 * status {@code APPROVED} ou {@code CANCELLED}.
	 *
	 * @param gateway
	 *            Gateway responsável pelo acesso aos dados de pagamento
	 * @return Lista de pagamentos expirados
	 */
	public static List<Payment> getExpiredPayments(PaymentGateway gateway) {

		return gateway.findExpiredPaymentsWithoutApprovedOrCancelled(LocalDateTime.now());
	}
}
