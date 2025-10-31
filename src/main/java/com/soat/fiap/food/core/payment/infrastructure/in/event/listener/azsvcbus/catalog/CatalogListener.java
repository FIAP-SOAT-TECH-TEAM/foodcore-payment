package com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.catalog;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Inicializa os processadores de eventos relacionados a estoque.
 *
 * <p>
 * Garante que todos os listeners de estoque sejam iniciados automaticamente
 * após o carregamento do contexto da aplicação.
 * </p>
 */
@RequiredArgsConstructor @Component
public class CatalogListener {

	private final ServiceBusProcessorClient catalogStockDebitErrorServiceBusProcessorClient;

	@PostConstruct
	public void run() {
		catalogStockDebitErrorServiceBusProcessorClient.start();
	}
}
