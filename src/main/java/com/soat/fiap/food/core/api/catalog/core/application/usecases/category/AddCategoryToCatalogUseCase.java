package com.soat.fiap.food.core.api.catalog.core.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CategoryInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CategoryMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Adicionar uma categoria em um catalogo.
 */
@Slf4j
public class AddCategoryToCatalogUseCase {

	/**
	 * Adiciona uma categoria em um catalogo.
	 *
	 * @param categoryInput
	 *            Categoria a ser adicionada em um catalogo
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Catalogo com a categoria adicionada
	 */
	public static Catalog addCategoryToCatalog(CategoryInput categoryInput, CatalogGateway gateway) {
		log.debug("Criando categoria: {}", categoryInput.name());

		var category = CategoryMapper.toDomain(categoryInput);
		var catalog = gateway.findById(categoryInput.catalogId());

		if (catalog.isEmpty()) {
			log.warn("Tentativa de cadastrar categoria com catalogo inexistente. ID catalogo: {}",
					categoryInput.catalogId());
			throw new CatalogNotFoundException("Catalogo", categoryInput.catalogId());
		}

		catalog.get().addCategory(category);

		return catalog.get();
	}
}
