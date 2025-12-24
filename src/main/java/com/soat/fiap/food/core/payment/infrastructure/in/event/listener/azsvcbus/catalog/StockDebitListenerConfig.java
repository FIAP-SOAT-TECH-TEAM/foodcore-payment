package com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.catalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import com.google.gson.Gson;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.StockDebitEventDto;
import com.soat.fiap.food.core.payment.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.catalog.handlers.StockDebitHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Listener responsável por processar eventos relacionados a débito de estoque,
 * via Azure Service Bus.
 *
 * <p>
 * Este listener recebe eventos e executa o tratamento necessário, incluindo a
 * inicialização de pagamentos e a publicação de eventos relacionados.
 * </p>
 */
@Configuration @Slf4j @RequiredArgsConstructor @Transactional
public class StockDebitListenerConfig {

	private final Gson gson;
	private final StockDebitHandler stockDebitHandler;

	@Bean
	public ServiceBusProcessorClient stockDebitServiceBusProcessorClient(ServiceBusClientBuilder builder) {

		return builder.processor()
				.queueName(ServiceBusConfig.STOCK_DEBIT_QUEUE)
				.receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
				.processMessage(context -> {
					StockDebitEventDto event = gson.fromJson(context.getMessage().getBody().toString(),
							StockDebitEventDto.class);
					stockDebitHandler.handle(event);
				})
				.processError(
						context -> log.error("Erro ao processar evento de estoque debitado", context.getException()))
				.buildProcessorClient();
	}
}
