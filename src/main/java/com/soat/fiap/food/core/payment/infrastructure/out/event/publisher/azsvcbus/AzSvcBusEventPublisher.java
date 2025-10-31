package com.soat.fiap.food.core.payment.infrastructure.out.event.publisher.azsvcbus;
import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.google.gson.Gson;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.PaymentApprovedEventDto;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.PaymentExpiredEventDto;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do {@link EventPublisherSource} usando Azure Service Bus.
 * <p>
 * Esta classe envia eventos de domínio para filas do Azure Service Bus
 * correspondentes. Cada método publica um tipo de evento específico.
 * </p>
 */
@Slf4j @Component @AllArgsConstructor
public class AzSvcBusEventPublisher implements EventPublisherSource {

	private final ServiceBusSenderClient paymentApprovedSender;
	private final ServiceBusSenderClient paymentExpiredSender;
	private final Gson gson;

	/**
	 * Publica um evento de pagamento aprovado na fila correspondente do Azure
	 * Service Bus.
	 *
	 * @param event
	 *            Evento de pagamento aprovado
	 */
	@Override
	public void publishPaymentApprovedEvent(PaymentApprovedEventDto event) {
		try {
			paymentApprovedSender.sendMessage(new ServiceBusMessage(gson.toJson(event)));
			log.info("Evento de pagamento aprovado publicado com sucesso: {}", event);
		} catch (Exception ex) {
			log.error("Erro ao publicar evento de pagamento aprovado", ex);
		}
	}

	/**
	 * Publica um evento de pagamento expirado na fila correspondente do Azure
	 * Service Bus.
	 *
	 * @param event
	 *            Evento de pagamento expirado
	 */
	@Override
	public void publishPaymentExpiredEvent(PaymentExpiredEventDto event) {
		try {
			paymentExpiredSender.sendMessage(new ServiceBusMessage(gson.toJson(event)));
			log.info("Evento de pagamento expirado publicado com sucesso: {}", event);
		} catch (Exception ex) {
			log.error("Erro ao publicar evento de pagamento expirado", ex);
		}
	}
}
