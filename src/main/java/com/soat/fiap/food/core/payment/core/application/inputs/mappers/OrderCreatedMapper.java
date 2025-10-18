package com.soat.fiap.food.core.payment.core.application.inputs.mappers;

import java.util.List;

import com.soat.fiap.food.core.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.OrderCreatedEventDto;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.OrderItemCreatedEventDto;

/**
 * Classe utilitária responsável por mapear {@link OrderCreatedEventDto} para o
 * DTO {@link OrderCreatedInput}, utilizado na inicialização de pagamento.
 */
public class OrderCreatedMapper {

	/**
	 * Converte um {@link OrderCreatedEventDto} em um {@link OrderCreatedInput}.
	 *
	 * @param event
	 *            Evento de criação de pedido.
	 * @return Input com os dados necessários para inicialização de pagamento.
	 */
	public static OrderCreatedInput toInput(OrderCreatedEventDto event) {
		List<OrderCreatedInput.OrderCreatedItemInput> itemInputs = event.getItems()
				.stream()
				.map(OrderCreatedMapper::toItemInput)
				.toList();

		return new OrderCreatedInput(event.getId(), event.getOrderNumber(), event.getUserId(), event.getTotalAmount(),
				itemInputs);
	}

	private static OrderCreatedInput.OrderCreatedItemInput toItemInput(OrderItemCreatedEventDto itemEvent) {
		return new OrderCreatedInput.OrderCreatedItemInput(itemEvent.getProductId(), itemEvent.getName(),
				itemEvent.getQuantity(), itemEvent.getUnitPrice(), itemEvent.getSubtotal(),
				itemEvent.getObservations());
	}
}
