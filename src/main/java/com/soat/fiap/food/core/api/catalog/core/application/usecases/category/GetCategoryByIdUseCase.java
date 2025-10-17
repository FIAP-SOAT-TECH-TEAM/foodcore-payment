package com.soat.fiap.food.core.api.catalog.core.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter categoria por identificador.
 */
@Slf4j
public class GetCategoryByIdUseCase {

	/**
	 * Busca uma categoria por ID dentro de um catálogo.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Categoria encontrada
	 */
	public static Category getCategoryById(Long catalogId, Long categoryId, CatalogGateway gateway) {
		var catalog = gateway.findById(catalogId);

		if (catalog.isEmpty()) {
			log.warn("Catalogo não encontrado. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		}

		return catalog.get().getCategoryById(categoryId);
	}
}
