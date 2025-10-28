package com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import com.nimbusds.jose.shaded.gson.Gson;
import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api.InitializePaymentController;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.OrderCreatedEventDto;
import com.soat.fiap.food.core.payment.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Listener para eventos de pedido criado no módulo de pagamentos via Azure
 * Service Bus.
 * <p>
 * Este listener consome mensagens do tópico
 * {@link ServiceBusConfig#ORDER_CREATED_TOPIC} e subscription
 * {@link ServiceBusConfig#PAYMENT_ORDER_CREATED_TOPIC_SUBSCRIPTION},
 * processando eventos {@link OrderCreatedEventDto}, iniciando o pagamento do
 * pedido e publicando eventos relacionados.
 */
@Component @Slf4j
public class OrderCreatedListener {

	private final Gson gson = new Gson();

	/**
	 * Construtor do listener de eventos de pedidos.
	 *
	 * @param paymentDataSource
	 *            Fonte de dados de pagamentos
	 * @param acquirerSource
	 *            Interface para o adquirente de pagamentos
	 * @param eventPublisherSource
	 *            Publicador de eventos
	 * @param connectionString
	 *            Connection string do Azure Service Bus
	 */
	public OrderCreatedListener(PaymentDataSource paymentDataSource, AcquirerSource acquirerSource,
			EventPublisherSource eventPublisherSource,
			@Value("${azsvcbus.connection-string}") String connectionString) {

		try (ServiceBusProcessorClient processor = new ServiceBusClientBuilder().connectionString(connectionString)
				.processor()
				.topicName(ServiceBusConfig.ORDER_CREATED_TOPIC)
				.subscriptionName(ServiceBusConfig.PAYMENT_ORDER_CREATED_TOPIC_SUBSCRIPTION)
				.receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
				.processMessage(context -> {
					OrderCreatedEventDto event = gson.fromJson(context.getMessage().getBody().toString(),
							OrderCreatedEventDto.class);
					handle(event, paymentDataSource, acquirerSource, eventPublisherSource);
				})
				.processError(context -> log.error("Erro no listener de pedido criado", context.getException()))
				.buildProcessorClient()) {

			processor.start();
		} catch (Exception e) {
			log.error("Falha ao iniciar OrderCreatedListener", e);
		}
	}

	/**
	 * Processa o evento de pedido criado.
	 *
	 * @param event
	 *            Evento de pedido criado
	 * @param paymentDataSource
	 *            Fonte de dados de pagamentos
	 * @param acquirerSource
	 *            Interface para o adquirente de pagamentos
	 * @param eventPublisherSource
	 *            Publicador de eventos
	 */
	private void handle(OrderCreatedEventDto event, PaymentDataSource paymentDataSource, AcquirerSource acquirerSource,
			EventPublisherSource eventPublisherSource) {

		log.info("Recebido evento de pedido criado: {} com valor total: {}", event.getId(), event.getTotalAmount());

		InitializePaymentController.initializePayment(event, paymentDataSource, acquirerSource, eventPublisherSource);

		log.info("Pagamento iniciado para o pedido: {}", event.getId());
	}
}
