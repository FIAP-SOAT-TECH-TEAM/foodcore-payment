package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.category;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CategoryMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.UpdateCategoryInCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.CategoryPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CategoryResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar categoria.
 */
@Slf4j
public class UpdateCategoryController {

	/**
	 * Atualiza uma categoria.
	 *
	 * @param catalogId
	 *            Identificador do catalogo atual da categoria
	 * @param categoryId
	 *            Identificador da categoria a ser atualizada
	 * @param categoryRequest
	 *            Categoria a ser atualizada
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @return Categoria atualizada com identificadores atualizados
	 */
	public static CategoryResponse updateCategory(Long catalogId, Long categoryId, CategoryRequest categoryRequest,
			CatalogDataSource catalogDataSource) {

		var gateway = new CatalogGateway(catalogDataSource);

		var categoryInput = CategoryMapper.toInput(categoryRequest);

		var catalog = UpdateCategoryInCatalogUseCase.updateCategoryInCatalog(catalogId, categoryId, categoryInput,
				gateway);

		var updatedCatalog = gateway.save(catalog);

		var updatedCategory = updatedCatalog.getCategoryById(categoryId);

		log.debug("Categoria atualizada com sucesso: {}", updatedCategory.getId());

		return CategoryPresenter.toCategoryResponse(updatedCategory);
	}
}
