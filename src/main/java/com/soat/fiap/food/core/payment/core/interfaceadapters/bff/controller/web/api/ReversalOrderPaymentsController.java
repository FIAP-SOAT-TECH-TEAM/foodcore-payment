package com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.payment.core.application.usecases.GetAllOrderPaymentsUseCase;
import com.soat.fiap.food.core.payment.core.application.usecases.PublishPaymentReversalEventUseCase;
import com.soat.fiap.food.core.payment.core.application.usecases.UpdatePaymentStatusUseCase;
import com.soat.fiap.food.core.payment.core.domain.events.PaymentReversalEvent;
import com.soat.fiap.food.core.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Estornar pagamentos de um pedido.
 */
@Slf4j
public class ReversalOrderPaymentsController {

	/**
	 * Estorna pagamentos
	 * <p>
	 * Para cada pagamento estornado:
	 * <ul>
	 * <li>Atualiza o status para {@code CANCELLED}</li>
	 * <li>Salva a atualização</li>
	 * <li>Publica um evento {@link PaymentReversalEvent}</li>
	 * </ul>
	 * @param orderId
	 *            ID do pedido
	 * @param paymentDataSource
	 *            Origem de dados dos pagamentos
	 * @param eventPublisherSource
	 *            Origem de publicação de eventos
	 */
	public static void reversalOrderPayments(Long orderId, PaymentDataSource paymentDataSource, EventPublisherSource eventPublisherSource) {

		var paymentGateway = new PaymentGateway(paymentDataSource);
		var orderPayments = GetAllOrderPaymentsUseCase.getAllOrderPayments(orderId, paymentGateway);
		var eventPublisherGateway = new EventPublisherGateway(eventPublisherSource);

		if (orderPayments.isEmpty()) {
			log.warn("Nenhum pagamento encontrado para o pedido: {}", orderId);
			throw new PaymentNotFoundException("Nenhum pagamento encontrado para o pedido: ", orderId);
		}

		for (Payment orderPayment : orderPayments) {
			log.info("Iniciando estorno do pagamento: {}, pedido: {}", orderPayment.getId(),
					orderPayment.getOrderId());

			var updatedPayment = UpdatePaymentStatusUseCase.updatePaymentStatus(orderPayment,
					PaymentStatus.CANCELLED);
			paymentGateway.save(updatedPayment);

			log.info("Pagamento estornado com sucesso: {}", orderPayment.getId());
		}

		PublishPaymentReversalEventUseCase.publishPaymentReversalEvent(orderId, eventPublisherGateway);
	}
}
