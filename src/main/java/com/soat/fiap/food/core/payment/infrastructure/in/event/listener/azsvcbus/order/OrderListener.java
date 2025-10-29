package com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.order;

import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Inicializa os processadores de eventos do módulo de Pagamentos.
 *
 * <p>
 * Este componente garante que o listener de pedidos criados seja iniciado
 * automaticamente após o carregamento do contexto da aplicação.
 * </p>
 */
@RequiredArgsConstructor @Component
public class OrderListener {

	private final ServiceBusProcessorClient paymentOrderCreatedTopicServiceBusProcessorClient;

	/**
	 * Inicia o processador configurado após a injeção de dependências.
	 */
	@PostConstruct
	public void run() {
		paymentOrderCreatedTopicServiceBusProcessorClient.start();
	}
}
