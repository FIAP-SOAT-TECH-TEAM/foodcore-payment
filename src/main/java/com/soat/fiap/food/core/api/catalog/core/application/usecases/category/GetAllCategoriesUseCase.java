package com.soat.fiap.food.core.api.catalog.core.application.usecases.category;

import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter todas categorias.
 */
@Slf4j
public class GetAllCategoriesUseCase {

	/**
	 * Lista todas as categorias de um catálogo.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Lista de categorias
	 */
	public static List<Category> getAllCategories(Long catalogId, CatalogGateway gateway) {

		var catalog = gateway.findById(catalogId);

		if (catalog.isEmpty()) {
			log.warn("Catalogo não encontrado. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		}

		var categories = catalog.get().getCategories();

		log.debug("Encontradas {} categorias", categories.size());

		return categories;
	}
}
