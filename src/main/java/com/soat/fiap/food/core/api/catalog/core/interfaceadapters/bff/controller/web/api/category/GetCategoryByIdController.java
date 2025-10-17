package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.category;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.GetCategoryByIdUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.CategoryPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CategoryResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Buscar uma categoria pelo seu ID.
 */
@Slf4j
public class GetCategoryByIdController {

	/**
	 * Busca uma categoria por ID dentro de um catálogo.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @return a categoria encontrada
	 */
	public static CategoryResponse getCategoryById(Long catalogId, Long categoryId,
			CatalogDataSource catalogDataSource) {
		log.debug("Buscando categoria de id: {} no catalogo de id: {}", categoryId, catalogId);

		var gateway = new CatalogGateway(catalogDataSource);

		var existingCategory = GetCategoryByIdUseCase.getCategoryById(catalogId, categoryId, gateway);

		return CategoryPresenter.toCategoryResponse(existingCategory);
	}
}
