package com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.payment.core.application.inputs.mappers.StockDebitMapper;
import com.soat.fiap.food.core.payment.core.application.usecases.InitializePaymentUseCase;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.StockDebitEventDto;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Inicializar pagamento.
 */
@Slf4j
public class InitializePaymentController {
	/**
	 * Inicializa o pagamento de um pedido a partir do evento
	 * {@link StockDebitEventDto}.
	 *
	 * @param event
	 *            Evento que representa um débito de estoque
	 * @param paymentDataSource
	 *            Origem de dados para o gateway de pagamento
	 * @param acquirerSource
	 *            Origem de dados do adquirente
	 */
	public static void initializePayment(StockDebitEventDto event, PaymentDataSource paymentDataSource,
			AcquirerSource acquirerSource) {
		log.info("Inicializando pagamento para o pedido {} no valor de {}", event.orderId, event.getTotalAmount());

		var paymentGateway = new PaymentGateway(paymentDataSource);
		var acquirerGateway = new AcquirerGateway(acquirerSource);

		var stockDebitInput = StockDebitMapper.toInput(event);

		var payment = InitializePaymentUseCase.initializePayment(stockDebitInput, paymentGateway, acquirerGateway);

		var savedPayment = paymentGateway.save(payment);

		log.info("Pagamento inicializado com ID: {}, qrCode: {}, pedido: {}", savedPayment.getId(),
				savedPayment.getQrCode(), savedPayment.getOrderId());
	}
}
