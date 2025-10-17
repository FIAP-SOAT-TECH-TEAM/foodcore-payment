package com.soat.fiap.food.core.api.catalog.core.application.inputs;

/**
 * Representa um DTO de entrada da aplicação (Application Layer) contendo os
 * dados necessários para criar ou manipular um catálogo.
 */
public record CatalogInput(String name) {

	/**
	 * Construtor para inicializar um {@code CatalogInput} com o nome do catálogo.
	 *
	 * @param name
	 *            O nome do catálogo.
	 */
	public CatalogInput {
	}
}
