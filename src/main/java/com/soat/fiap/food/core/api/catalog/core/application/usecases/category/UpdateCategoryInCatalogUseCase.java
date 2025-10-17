package com.soat.fiap.food.core.api.catalog.core.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CategoryInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CategoryMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar categoria existente em um catalogo.
 */
@Slf4j
public class UpdateCategoryInCatalogUseCase {

	/**
	 * Atualiza uma categoria existente em um catalogo.
	 *
	 * @param catalogId
	 *            Identificador do catalogo atual da categoria
	 * @param categoryId
	 *            Identificador da categoria a ser atualizada
	 * @param categoryInput
	 *            Categoria a ser atualizada
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Catalogo com a categoria atualizada
	 */
	public static Catalog updateCategoryInCatalog(Long catalogId, Long categoryId, CategoryInput categoryInput,
			CatalogGateway gateway) {
		var category = CategoryMapper.toDomain(categoryInput);
		var existingCatalog = gateway.findById(catalogId);
		category.setId(categoryId);

		log.debug("Atualizando categoria: {}", categoryId);

		if (existingCatalog.isEmpty()) {
			log.warn("Tentativa de atualizar categoria com catálogo inexistente. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		} else if (!categoryInput.catalogId().equals(catalogId)) {

			var newCatalog = gateway.findById(categoryInput.catalogId());

			if (newCatalog.isEmpty()) {
				log.warn("Tentativa de mover categoria para catálogo inexistente. Id: {}", categoryInput.catalogId());
				throw new CatalogNotFoundException("Catalogo", categoryInput.catalogId());
			}

			existingCatalog.get().moveCatalogCategory(newCatalog.get(), categoryId);
			log.debug("Categoria movida com sucesso para catálogo: {}", categoryInput.catalogId());

			newCatalog.get().updateCategory(category);

			return newCatalog.get();
		}

		existingCatalog.get().updateCategory(category);

		return existingCatalog.get();
	}
}
