package com.soat.fiap.food.core.payment.core.application.inputs;

import java.math.BigDecimal;

/**
 * Representa um DTO de entrada da aplicação (Application Layer) contendo os
 * dados dos itens débitados do estoque.
 * <p>
 *
 * @param productId
 *            Id do produto debitado no estoque
 * @param name
 *            Nome do produto debitado
 * @param quantity
 *            Quantidade debitada do estoque
 * @param unitPrice
 *            Valor unitário do produto debitado
 * @param subtotal
 *            Subtotal referente à quantidade debitada
 * @param observations
 *            Observações relacionadas ao item debitado
 */
public record StockDebitItemInput(Long productId, String name, Integer quantity, BigDecimal unitPrice,
		BigDecimal subtotal, String observations) {

	/**
	 * Construtor compacto que inicializa um {@code StockDebitItemInput} com todos
	 * os campos necessários.
	 *
	 * @param productId
	 *            Id do produto debitado
	 * @param name
	 *            Nome do produto debitado
	 * @param quantity
	 *            Quantidade debitada
	 * @param unitPrice
	 *            Valor unitário do produto debitado
	 * @param subtotal
	 *            Subtotal referente ao item debitado
	 * @param observations
	 *            Observações do item debitado
	 */
	public StockDebitItemInput {
	}
}
