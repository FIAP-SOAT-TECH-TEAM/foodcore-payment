package com.soat.fiap.food.core.api.catalog.core.application.inputs;

import java.math.BigDecimal;

/**
 * Representa um DTO de entrada da aplicação (Application Layer) contendo os
 * dados necessários para criar ou manipular um produto.
 */
public record ProductInput(Long categoryId, String name, String description, BigDecimal price, Integer stockQuantity,
		Integer displayOrder) {

	/**
	 * Construtor para inicializar um {@code ProductInput} com os campos
	 * necessários.
	 *
	 * @param categoryId
	 *            ID da categoria do produto.
	 * @param name
	 *            Nome do produto.
	 * @param description
	 *            Descrição do produto.
	 * @param price
	 *            Preço do produto.
	 * @param stockQuantity
	 *            Quantidade de estoque do produto.
	 * @param displayOrder
	 *            Ordem de exibição do produto.
	 */
	public ProductInput {
	}
}
