package com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.api.payment.core.application.inputs.mappers.OrderCreatedMapper;
import com.soat.fiap.food.core.api.payment.core.application.usecases.InitializePaymentUseCase;
import com.soat.fiap.food.core.api.payment.core.application.usecases.PublishPaymentInitializationErrorEventUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.OrderCreatedEventDto;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Inicializar pagamento.
 */
@Slf4j
public class InitializePaymentController {
	/**
	 * Inicializa o pagamento de um pedido a partir do evento
	 * {@link OrderCreatedEventDto}.
	 *
	 * @param event
	 *            Evento que representa um pedido criado
	 * @param paymentDataSource
	 *            Origem de dados para o gateway de pagamento
	 * @param acquirerSource
	 *            Origem de dados do adquirente
	 * @param eventPublisherSource
	 *            Origem de publicação de eventos
	 */
	public static void initializePayment(OrderCreatedEventDto event, PaymentDataSource paymentDataSource,
			AcquirerSource acquirerSource, EventPublisherSource eventPublisherSource) {
		try {
			log.info("Inicializando pagamento para o pedido {} no valor de {}", event.getId(), event.getTotalAmount());

			var paymentGateway = new PaymentGateway(paymentDataSource);
			var acquirerGateway = new AcquirerGateway(acquirerSource);

			var orderCreatedInput = OrderCreatedMapper.toInput(event);

			var payment = InitializePaymentUseCase.initializePayment(orderCreatedInput, paymentGateway,
					acquirerGateway);

			var savedPayment = paymentGateway.save(payment);

			log.info("Pagamento inicializado com ID: {}, qrCode: {}, pedido: {}", savedPayment.getId(),
					savedPayment.getQrCode(), savedPayment.getOrderId());
		} catch (Exception ex) {
			log.warn("Erro na inicialização do pagamento. Pedido: {}, Causa: {}", event.getId(), ex.getMessage());

			var eventPublisherGateway = new EventPublisherGateway(eventPublisherSource);

			PublishPaymentInitializationErrorEventUseCase.publishPaymentInitializationErrorEvent(event.getId(),
					ex.getMessage(), eventPublisherGateway);
		}
	}
}
