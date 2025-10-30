package com.soat.fiap.food.core.payment.infrastructure.out.event.publisher.azsvcbus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.google.gson.Gson;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.PaymentApprovedEventDto;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.PaymentExpiredEventDto;
import com.soat.fiap.food.core.payment.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do {@link EventPublisherSource} usando Azure Service Bus.
 * <p>
 * Esta classe envia eventos de domínio para filas do Azure Service Bus
 * correspondentes. Cada método publica um tipo de evento específico.
 * </p>
 */
@Slf4j @Component
public class AzSvcBusEventPublisher implements EventPublisherSource {

	private final ServiceBusSenderClient paymentApprovedSender;
	private final ServiceBusSenderClient paymentExpiredSender;
	private final Gson gson;

	/**
	 * Construtor que inicializa os clients do Azure Service Bus usando a connection
	 * string.
	 *
	 * @param connectionString
	 *            Connection string do Azure Service Bus, lida do application.yaml
	 */
	public AzSvcBusEventPublisher(@Value("${azsvcbus.connection-string}") String connectionString, Gson gson) {

		this.paymentApprovedSender = new ServiceBusClientBuilder().connectionString(connectionString)
				.sender()
				.queueName(ServiceBusConfig.PAYMENT_APPROVED_QUEUE)
				.buildClient();

		this.paymentExpiredSender = new ServiceBusClientBuilder().connectionString(connectionString)
				.sender()
				.queueName(ServiceBusConfig.PAYMENT_EXPIRED_QUEUE)
				.buildClient();

		this.gson = gson;
	}

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

	/**
	 * Fecha todos os clients do Azure Service Bus ao destruir o bean.
	 */
	@PreDestroy
	public void close() {
		log.info("Fechando clients do Azure Service Bus...");
		if (paymentApprovedSender != null)
			paymentApprovedSender.close();
		if (paymentExpiredSender != null)
			paymentExpiredSender.close();
	}
}
