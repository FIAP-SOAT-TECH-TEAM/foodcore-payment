package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.category;

import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.GetAllCategoriesUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.CategoryPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CategoryResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Obter todos as categorias.
 */
@Slf4j
public class GetAllCategoriesController {

	/**
	 * Obtém todos as categorias.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @return Categorias encontradas
	 */
	public static List<CategoryResponse> getAllCategories(Long catalogId, CatalogDataSource catalogDataSource) {
		log.debug("Buscando todas as categorias do catalogo de id: {}", catalogId);

		var gateway = new CatalogGateway(catalogDataSource);

		var existingCategories = GetAllCategoriesUseCase.getAllCategories(catalogId, gateway);

		return CategoryPresenter.toListCategoryResponse(existingCategories);
	}
}
