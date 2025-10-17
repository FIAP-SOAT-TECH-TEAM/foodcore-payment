package com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetExpiredPaymentsUseCase;
import com.soat.fiap.food.core.api.payment.core.application.usecases.PublishPaymentExpiredEventUseCase;
import com.soat.fiap.food.core.api.payment.core.application.usecases.UpdatePaymentStatusUseCase;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Processar pagamentos expirados.
 */
@Slf4j
public class ProcessExpiredPaymentsController {

	/**
	 * Processa pagamentos que expiraram e ainda estão com status pendente.
	 * <p>
	 * Para cada pagamento expirado:
	 * <ul>
	 * <li>Atualiza o status para {@code CANCELLED}</li>
	 * <li>Salva a atualização</li>
	 * <li>Publica um evento
	 * {@link com.soat.fiap.food.core.api.payment.core.domain.events.PaymentExpiredEvent}</li>
	 * </ul>
	 *
	 * @param paymentDataSource
	 *            Origem de dados dos pagamentos
	 * @param eventPublisherSource
	 *            Origem de publicação de eventos
	 */
	public static void processExpiredPayments(PaymentDataSource paymentDataSource,
			EventPublisherSource eventPublisherSource) {

		var paymentGateway = new PaymentGateway(paymentDataSource);
		var eventPublisherGateway = new EventPublisherGateway(eventPublisherSource);
		var expiredPayments = GetExpiredPaymentsUseCase.getExpiredPayments(paymentGateway);

		if (expiredPayments.isEmpty()) {
			log.info("Nenhum pagamento pendente expirado encontrado!");
			return;
		}

		for (Payment expiredPayment : expiredPayments) {
			log.info("Iniciando processamento expirado: {}, pedido: {}, expirado em: {}", expiredPayment.getId(),
					expiredPayment.getOrderId(), expiredPayment.getExpiresIn().toString());

			var updatedPayment = UpdatePaymentStatusUseCase.updatePaymentStatus(expiredPayment,
					PaymentStatus.CANCELLED);
			paymentGateway.save(updatedPayment);

			log.info("Pagamento expirado cancelado: {}", expiredPayment.getId());

			PublishPaymentExpiredEventUseCase.publishPaymentExpiredEvent(updatedPayment, eventPublisherGateway);
		}
	}
}
