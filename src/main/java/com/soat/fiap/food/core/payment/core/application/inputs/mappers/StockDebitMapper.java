package com.soat.fiap.food.core.payment.core.application.inputs.mappers;

import com.soat.fiap.food.core.payment.core.application.inputs.StockDebitInput;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.StockDebitEventDto;

/**
 * Classe utilitária responsável por mapear {@link StockDebitEventDto} para o
 * DTO {@link StockDebitInput}, utilizado na inicialização de pagamento.
 */
public class StockDebitMapper {

	/**
	 * Converte um {@link StockDebitEventDto} em um {@link StockDebitInput}.
	 *
	 * @param event
	 *            Evento de débito de estoque.
	 * @return Input com os dados de débito de estoque.
	 */
	public static StockDebitInput toInput(StockDebitEventDto event) {
		return new StockDebitInput(event.getOrderId(), event.getOrderNumber(), event.getUserId(),
				event.getTotalAmount(), StockDebitItemMapper.toInputList(event.getItems()));
	}
}
