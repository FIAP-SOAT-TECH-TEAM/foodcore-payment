package com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.catalog.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api.InitializePaymentController;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.StockDebitEventDto;
import com.soat.fiap.food.core.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler responsável por processar eventos de débito de estoque.
 *
 * <p>
 * Quando um estoque é debitado, este handler inicializa o pagamento
 * correspondente.
 * </p>
 */
@Slf4j @Service @RequiredArgsConstructor
public class StockDebitHandler {

	private final PaymentDataSource paymentDataSource;
	private final AcquirerSource acquirerSource;

	/**
	 * Processa o evento de débito de estoque.
	 *
	 * @param event
	 *            Evento de débito de estoque
	 */
	@Transactional
	public void handle(StockDebitEventDto event) {
		log.info("Evento de estoque debitado recebido: {}", event.orderId);

		InitializePaymentController.initializePayment(event, paymentDataSource, acquirerSource);

		log.info("Processamento de pagamento concluído para o pedido: {}", event.orderId);
	}
}
