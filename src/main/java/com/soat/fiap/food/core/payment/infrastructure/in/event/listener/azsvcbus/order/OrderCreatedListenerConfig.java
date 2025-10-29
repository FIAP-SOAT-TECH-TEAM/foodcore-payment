package com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
 * Listener responsável por processar eventos relacionados a pedidos criados no
 * módulo de Pagamentos via Azure Service Bus.
 *
 * <p>
 * Este listener recebe eventos e executa o tratamento necessário, incluindo a
 * inicialização de pagamentos e a publicação de eventos relacionados.
 * </p>
 */
@Configuration @Slf4j
public class OrderCreatedListenerConfig {

	private final Gson gson = new Gson();

	/**
	 * Cria o processador responsável por consumir mensagens de criação de pedido
	 * para o módulo de Pagamentos.
	 *
	 * @param paymentDataSource
	 *            Fonte de dados de pagamentos
	 * @param acquirerSource
	 *            Interface para o adquirente de pagamentos
	 * @param eventPublisherSource
	 *            Publicador de eventos
	 * @param connectionString
	 *            String de conexão do Azure Service Bus
	 * @return Cliente processador configurado
	 */
	@Bean
	public ServiceBusProcessorClient paymentOrderCreatedTopicServiceBusProcessorClient(
			PaymentDataSource paymentDataSource, AcquirerSource acquirerSource,
			EventPublisherSource eventPublisherSource,
			@Value("${azsvcbus.connection-string}") String connectionString) {

		return new ServiceBusClientBuilder().connectionString(connectionString)
				.processor()
				.topicName(ServiceBusConfig.ORDER_CREATED_TOPIC)
				.subscriptionName(ServiceBusConfig.PAYMENT_ORDER_CREATED_TOPIC_SUBSCRIPTION)
				.receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
				.processMessage(context -> {
					OrderCreatedEventDto event = gson.fromJson(context.getMessage().getBody().toString(),
							OrderCreatedEventDto.class);
					handle(event, paymentDataSource, acquirerSource, eventPublisherSource);
				})
				.processError(context -> log.error("Erro ao processar evento de pedido criado", context.getException()))
				.buildProcessorClient();
	}

	/**
	 * Executa o tratamento do evento recebido.
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

		log.info("Evento de pedido criado recebido: {}", event.getId());

		InitializePaymentController.initializePayment(event, paymentDataSource, acquirerSource, eventPublisherSource);

		log.info("Processamento de pagamento concluído para o pedido: {}", event.getId());
	}
}
