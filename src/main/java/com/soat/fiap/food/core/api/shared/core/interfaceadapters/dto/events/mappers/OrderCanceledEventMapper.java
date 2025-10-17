package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.mappers;

import java.util.List;

import com.soat.fiap.food.core.api.order.core.domain.events.OrderCanceledEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderItemCanceledEvent;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.OrderCanceledEventDto;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.OrderItemCanceledEventDto;

/**
 * Classe utilitária responsável por mapear {@link OrderCanceledEvent} para o
 * DTO {@link OrderCanceledEventDto}, utilizado para transporte de dados do
 * evento de pedido cancelado.
 */
public class OrderCanceledEventMapper {

	/**
	 * Converte um {@link OrderCanceledEvent} em um {@link OrderCanceledEventDto}.
	 *
	 * @param event
	 *            Evento de cancelamento de pedido.
	 * @return DTO com os dados do pedido cancelado.
	 */
	public static OrderCanceledEventDto toDto(OrderCanceledEvent event) {
		List<OrderItemCanceledEventDto> itemDtos = event.getItems()
				.stream()
				.map(OrderCanceledEventMapper::toItemDto)
				.toList();

		OrderCanceledEventDto dto = new OrderCanceledEventDto();
		dto.setId(event.getId());
		dto.setStatus(event.getStatus());
		dto.setItems(itemDtos);

		return dto;
	}

	/**
	 * Converte um item do evento {@link OrderCanceledEvent} em seu respectivo DTO
	 * {@link OrderItemCanceledEventDto}.
	 *
	 * @param itemItem
	 *            Evento de item cancelado do pedido.
	 * @return DTO representando o item cancelado.
	 */
	private static OrderItemCanceledEventDto toItemDto(OrderItemCanceledEvent itemItem) {
		OrderItemCanceledEventDto dto = new OrderItemCanceledEventDto();
		dto.setProductId(itemItem.getProductId());
		dto.setName(itemItem.getName());
		dto.setQuantity(itemItem.getQuantity());
		dto.setUnitPrice(itemItem.getUnitPrice());
		dto.setSubtotal(itemItem.getSubtotal());
		dto.setObservations(itemItem.getObservations());
		return dto;
	}
}
