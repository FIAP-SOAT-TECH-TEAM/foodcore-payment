package com.soat.fiap.food.core.api.catalog.core.application.inputs;

/**
 * Representa um DTO de entrada da aplicação (Application Layer) contendo os
 * dados necessários para criar ou manipular uma categoria.
 */
public record CategoryInput(Long catalogId, String name, String description, boolean active, Integer displayOrder) {

	/**
	 * Construtor para inicializar um {@code CategoryInput} com os dados
	 * necessários.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param name
	 *            Nome da categoria
	 * @param description
	 *            Descrição da categoria
	 * @param active
	 *            Status da categoria
	 * @param displayOrder
	 *            Ordem de exibição da categoria
	 */
	public CategoryInput {
	}
}
