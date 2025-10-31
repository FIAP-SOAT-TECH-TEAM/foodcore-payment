package com.soat.fiap.food.core.payment.core.application.inputs.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.soat.fiap.food.core.payment.core.application.inputs.StockDebitItemInput;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.StockDebitItemEventDto;

/**
 * Classe utilitária responsável por mapear {@link StockDebitItemEventDto} para
 * o DTO {@link StockDebitItemInput}.
 */
public class StockDebitItemMapper {

	/**
	 * Converte um {@link StockDebitItemEventDto} em um {@link StockDebitItemInput}.
	 *
	 * @param event
	 *            Evento de débito de item de estoque.
	 * @return Input com os dados do item debitado do estoque.
	 */
	public static StockDebitItemInput toInput(StockDebitItemEventDto event) {
		return new StockDebitItemInput(event.getProductId(), event.getName(), event.getQuantity(), event.getUnitPrice(),
				event.getSubtotal(), event.getObservations());
	}

	/**
	 * Converte uma lista de {@link StockDebitItemEventDto} em uma lista de
	 * {@link StockDebitItemInput}.
	 *
	 * @param events
	 *            Lista de eventos de débito de itens de estoque.
	 * @return Lista de inputs com os dados dos itens debitados do estoque.
	 */
	public static List<StockDebitItemInput> toInputList(List<StockDebitItemEventDto> events) {
		if (events == null || events.isEmpty()) {
			return List.of();
		}

		return events.stream().map(StockDebitItemMapper::toInput).collect(Collectors.toList());
	}
}
