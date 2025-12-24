package com.soat.fiap.food.core.payment.core.interfaceadapters.controller;

import com.soat.fiap.food.core.payment.core.application.inputs.mappers.AcquirerNotificationMapper;
import com.soat.fiap.food.core.payment.core.application.usecases.ProcessPaymentNotificationUseCase;
import com.soat.fiap.food.core.payment.core.application.usecases.PublishPaymentApprovedEventUseCase;
import com.soat.fiap.food.core.payment.core.domain.exceptions.PaymentAlreadyProcessedException;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.request.AcquirerNotificationRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Processar notificação de pagamento do adquirente.
 */
@Slf4j
public class ProcessPaymentNotificationController {

	/**
	 * Processa a notificação de pagamento recebida via webhook do adquirente.
	 *
	 * @param acquirerNotificationRequest
	 *            Notificação recebida do adquirente
	 * @param paymentDataSource
	 *            Origem de dados do pagamento
	 * @param acquirerSource
	 *            Fonte de dados do adquirente
	 * @param eventPublisherSource
	 *            Fonte de publicação de eventos
	 */
	public static void processPaymentNotification(AcquirerNotificationRequest acquirerNotificationRequest,
			PaymentDataSource paymentDataSource, AcquirerSource acquirerSource,
			EventPublisherSource eventPublisherSource) {

		var acquirerNotificationInput = AcquirerNotificationMapper.toInput(acquirerNotificationRequest);
		var acquirerGateway = new AcquirerGateway(acquirerSource);
		var paymentGateway = new PaymentGateway(paymentDataSource);
		var eventPublisherGateway = new EventPublisherGateway(eventPublisherSource);

		try {
			var payment = ProcessPaymentNotificationUseCase.processPaymentNotification(acquirerNotificationInput,
					acquirerGateway, paymentGateway);

			var savedPayment = paymentGateway.save(payment);

			if (payment.getStatus() == PaymentStatus.APPROVED) {
				PublishPaymentApprovedEventUseCase.publishPaymentApprovedEvent(savedPayment, eventPublisherGateway);
				log.info("Evento de pagamento aprovado publicado! PaymentId: {}, OrderId: {}!", payment.getId(),
						payment.getOrderId());
			}
		} catch (PaymentAlreadyProcessedException exception) {
			log.warn(exception.getMessage());
		}
	}
}
