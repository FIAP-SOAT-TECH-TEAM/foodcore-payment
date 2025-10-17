package com.soat.fiap.food.core.api.order.infrastructure.in.event.listener.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.bff.controller.web.api.UpdateOrderStatusController;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.OrderStatusRequest;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.PaymentApprovedEventDto;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.PaymentExpiredEventDto;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.PaymentInitializationErrorEventDto;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq.config.RabbitMqQueueConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * Ouvinte de eventos de pagamento no módulo de pedidos via RabbitMQ
 */
@Component @Slf4j
public class OrderPaymentEventListener {

	private final OrderDataSource orderDataSource;
	private final PaymentDataSource paymentDataSource;
	private final EventPublisherSource eventPublisherSource;

	/**
	 * Construtor do listener de eventos de pagamento.
	 *
	 * @param orderDataSource
	 *            Fonte de dados de pedidos
	 * @param paymentDataSource
	 *            Fonte de dados de pagamentos
	 * @param eventPublisherSource
	 *            Publicador de eventos
	 */
	public OrderPaymentEventListener(OrderDataSource orderDataSource, PaymentDataSource paymentDataSource,
			EventPublisherSource eventPublisherSource) {
		this.orderDataSource = orderDataSource;
		this.paymentDataSource = paymentDataSource;
		this.eventPublisherSource = eventPublisherSource;
	}

	/**
	 * Processa o evento de pagamento aprovado.
	 * <p>
	 * Atualiza o status do pedido para PREPARING e registra log de notificação.
	 * </p>
	 *
	 * @param event
	 *            Evento de pagamento aprovado
	 */
	@RabbitListener(queues = RabbitMqQueueConfig.PAYMENT_APPROVED_QUEUE)
	public void handlePaymentApprovedEvent(PaymentApprovedEventDto event) {
		log.info("Módulo Order: Recebido evento de pagamento aprovado para o pedido: {}, valor: {}", event.getOrderId(),
				event.getAmount());

		var orderUpdateStatusRequest = new OrderStatusRequest(OrderStatus.PREPARING);
		UpdateOrderStatusController.updateOrderStatus(event.getOrderId(), orderUpdateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);

		log.info("Pedido {} atualizado para status PREPARING após pagamento aprovado", event.getOrderId());
	}

	/**
	 * Processa o evento de erro na inicialização do pagamento.
	 * <p>
	 * Atualiza o status do pedido para CANCELLED e registra log de notificação.
	 * </p>
	 *
	 * @param event
	 *            Evento de erro na inicialização do pagamento
	 */
	@RabbitListener(queues = RabbitMqQueueConfig.PAYMENT_INITIALIZATION_ERROR_QUEUE)
	public void handlePaymentInitializationErrorEvent(PaymentInitializationErrorEventDto event) {
		log.info("Módulo Order: Recebido evento de erro na inicialização do pagamento. Pedido: {}", event.getOrderId());

		var orderUpdateStatusRequest = new OrderStatusRequest(OrderStatus.CANCELLED);
		UpdateOrderStatusController.updateOrderStatus(event.getOrderId(), orderUpdateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);
	}

	/**
	 * Processa o evento de pagamento expirado.
	 * <p>
	 * Atualiza o status do pedido para CANCELLED e registra log de notificação.
	 * </p>
	 *
	 * @param event
	 *            Evento de pagamento expirado
	 */
	@RabbitListener(queues = RabbitMqQueueConfig.PAYMENT_EXPIRED_QUEUE)
	public void handlePaymentExpiredEvent(PaymentExpiredEventDto event) {
		log.info("Módulo Order: Recebido evento de pagamento expirado. Pedido: {}, Pagamento: {}", event.getOrderId(),
				event.getPaymentId());

		var orderUpdateStatusRequest = new OrderStatusRequest(OrderStatus.CANCELLED);
		UpdateOrderStatusController.updateOrderStatus(event.getOrderId(), orderUpdateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);
	}
}
