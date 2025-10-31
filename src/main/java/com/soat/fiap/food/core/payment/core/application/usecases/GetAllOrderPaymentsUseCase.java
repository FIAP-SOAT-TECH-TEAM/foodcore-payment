package com.soat.fiap.food.core.payment.core.application.usecases;

import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Caso de uso: Buscar todos os pagamentos de um pedido.
 */
@Slf4j
public class GetAllOrderPaymentsUseCase {

	/**
	 * Recupera todos os pagamentos de um pedido.
	 * @param orderId
	 *            ID do pedido
	 * @param gateway
	 *            Gateway responsável pelo acesso aos dados de pagamento
	 * @return Lista de pagamentos do pedido
	 */
	public static List<Payment> getAllOrderPayments(Long orderId, PaymentGateway gateway) {

		return gateway.findByOrderId(orderId);
	}
}
