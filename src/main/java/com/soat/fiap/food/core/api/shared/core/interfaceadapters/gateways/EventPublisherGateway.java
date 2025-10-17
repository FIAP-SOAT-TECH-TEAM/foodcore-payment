package com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.catalog.core.domain.events.ProductCreatedEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderCanceledEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentExpiredEvent;
import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentInitializationErrorEvent;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.mappers.*;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

/**
 * Gateway para publicação de eventos de domínio.
 * <p>
 * Este gateway delega a publicação de eventos ao {@link EventPublisherSource},
 * fornecendo métodos específicos para cada tipo de evento de domínio.
 * </p>
 */
public class EventPublisherGateway {

	private final EventPublisherSource eventPublisherSource;

	public EventPublisherGateway(EventPublisherSource eventPublisherSource) {
		this.eventPublisherSource = eventPublisherSource;
	}

	/**
	 * Publica um evento de pedido criado.
	 *
	 * @param event
	 *            Evento contendo informações do pedido criado.
	 */
	public void publishOrderCreatedEvent(OrderCreatedEvent event) {
		var eventDto = OrderCreatedEventMapper.toDto(event);

		eventPublisherSource.publishOrderCreatedEvent(eventDto);
	}

	/**
	 * Publica um evento de pedido cancelado.
	 *
	 * @param event
	 *            Evento contendo informações do pedido cancelado.
	 */
	public void publishOrderCanceledEvent(OrderCanceledEvent event) {
		var eventDto = OrderCanceledEventMapper.toDto(event);

		eventPublisherSource.publishOrderCanceledEvent(eventDto);
	}

	/**
	 * Publica um evento de produto criado.
	 *
	 * @param event
	 *            Evento contendo informações do produto criado.
	 */
	public void publishProductCreatedEvent(ProductCreatedEvent event) {
		var eventDto = ProductCreatedEventMapper.toDto(event);

		eventPublisherSource.publishProductCreatedEvent(eventDto);
	}

	/**
	 * Publica um evento de pagamento aprovado.
	 *
	 * @param event
	 *            Evento contendo informações do pagamento aprovado.
	 */
	public void publishPaymentApprovedEvent(PaymentApprovedEvent event) {
		var eventDto = PaymentApprovedEventMapper.toDto(event);

		eventPublisherSource.publishPaymentApprovedEvent(eventDto);
	}

	/**
	 * Publica um evento de pagamento expirado.
	 *
	 * @param event
	 *            Evento contendo informações do pagamento expirado.
	 */
	public void publishPaymentExpiredEvent(PaymentExpiredEvent event) {
		var eventDto = PaymentExpiredEventMapper.toDto(event);

		eventPublisherSource.publishPaymentExpiredEvent(eventDto);
	}

	/**
	 * Publica um evento de erro na inicialização do pagamento.
	 *
	 * @param event
	 *            Evento contendo informações do erro ocorrido durante a
	 *            inicialização do pagamento.
	 */
	public void publishPaymentInitializationErrorEvent(PaymentInitializationErrorEvent event) {
		var eventDto = PaymentInitializationErrorEventMapper.toDto(event);

		eventPublisherSource.publishPaymentInitializationErrorEvent(eventDto);
	}
}
