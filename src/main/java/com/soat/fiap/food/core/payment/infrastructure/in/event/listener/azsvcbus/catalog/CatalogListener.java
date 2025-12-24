package com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.catalog;

import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Inicializa os processadores de eventos relacionados a estoque.
 *
 * <p>
 * Garante que todos os listeners de catálogo sejam iniciados automaticamente
 * após o carregamento do contexto da aplicação.
 * </p>
 */
@RequiredArgsConstructor @Component
public class CatalogListener {

	private final ServiceBusProcessorClient stockDebitServiceBusProcessorClient;

	@PostConstruct
	public void run() {
		stockDebitServiceBusProcessorClient.start();
	}
}
