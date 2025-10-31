package com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.catalog;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.SubQueue;
import com.google.gson.Gson;
import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api.ReversalOrderPaymentsController;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.OrderCreatedEventDto;
import com.soat.fiap.food.core.payment.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Listener responsável por processar eventos de erro no débito de estoque.
 *
 * @see <a href="https://learn.microsoft.com/pt-br/java/api/overview/azure/messaging-servicebus-readme?view=azure-java-stable#create-a-dead-letter-queue-receiver">Create a dead-letter queue Receiver - Azure Service Bus</a>
 */
@Configuration @Slf4j @RequiredArgsConstructor @Transactional
public class CatalogStockDebitErrorListenerConfig {

	private final Gson gson;

	@Bean
	public ServiceBusProcessorClient catalogStockDebitErrorServiceBusProcessorClient(
			EventPublisherSource eventPublisherSource,
			PaymentDataSource paymentDataSource,
			@Value("${azsvcbus.connection-string}") String connectionString) {

		return new ServiceBusClientBuilder().connectionString(connectionString)
				.processor()
				.topicName(ServiceBusConfig.ORDER_CREATED_TOPIC)
				.subscriptionName(ServiceBusConfig.CATALOG_ORDER_CREATED_TOPIC_SUBSCRIPTION)
				.subQueue(SubQueue.DEAD_LETTER_QUEUE)
				.processMessage(context -> {
					OrderCreatedEventDto event = gson.fromJson(context.getMessage().getBody().toString(),
							OrderCreatedEventDto.class);
					handle(event, paymentDataSource, eventPublisherSource);
				})
				.processError(context -> log.error("Erro ao processar erro de débito de estoque",
						context.getException()))
				.buildProcessorClient();
	}

	private void handle(OrderCreatedEventDto event,
						PaymentDataSource paymentDataSource, EventPublisherSource eventPublisherSource) {
		log.info("Evento de erro no débito de estoque recebido: {}", event.getId());

		ReversalOrderPaymentsController.reversalOrderPayments(event.getId(), paymentDataSource, eventPublisherSource);

		log.info("Status do pedido atualizado após erro no débito de estoque: {}", event.getId());
	}
}
