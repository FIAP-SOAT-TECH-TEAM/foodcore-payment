package com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.order.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api.ReversalOrderPaymentsController;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.OrderCanceledEventDto;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler responsável por processar eventos de cancelamento de pedido.
 *
 * <p>
 * Quando um pedido é cancelado, este handler realiza o estorno dos pagamentos
 * associados.
 * </p>
 */
@Slf4j @Service @RequiredArgsConstructor
public class OrderCanceledHandler {

	private final PaymentDataSource paymentDataSource;

	/**
	 * Processa o evento de cancelamento de pedido.
	 *
	 * @param event
	 *            Evento de cancelamento de pedido
	 */
	@Transactional
	public void handle(OrderCanceledEventDto event) {
		log.info("Evento de cancelamento de pedido recebido: {}", event.getId());

		ReversalOrderPaymentsController.reversalOrderPayments(event.getId(), paymentDataSource);

		log.info("Status do pagamento do pedido atualizado após cancelamento do pedido: {}", event.getId());
	}
}
