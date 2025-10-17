package com.soat.fiap.food.core.api.catalog.core.application.inputs;

/**
 * Representa um DTO de entrada da aplicação (Application Layer) contendo os
 * dados necessários para criar ou manipular um estoque.
 *
 * @param quantity
 *            Quantidade de itens no estoque
 */
public record StockInput(Integer quantity) {

	/**
	 * Construtor para inicializar um {@code StockInput} com a quantidade de
	 * estoque.
	 *
	 * @param quantity
	 *            Quantidade de itens.
	 */
	public StockInput {
	}
}
