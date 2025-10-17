package com.soat.fiap.food.core.api.catalog.core.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Remover categoria de um catalogo.
 */
@Slf4j
public class RemoveCategoryFromCatalogUseCase {

	/**
	 * Remove uma categoria de um catálogo.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @return Catalogo sem a categoria informada
	 */
	public static Catalog removeCategoryFromCatalog(Long catalogId, Long categoryId, CatalogGateway gateway) {
		var catalog = gateway.findById(catalogId);

		if (catalog.isEmpty()) {
			log.warn("Tentativa de excluir categoria com catálogo inexistente. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		}

		catalog.get().removeCategory(categoryId, true);

		return catalog.get();
	}
}
