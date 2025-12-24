package com.soat.fiap.food.core.payment.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.payment.core.domain.events.PaymentExpiredEvent;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.mappers.PaymentApprovedEventMapper;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.mappers.PaymentExpiredEventMapper;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;

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
}
