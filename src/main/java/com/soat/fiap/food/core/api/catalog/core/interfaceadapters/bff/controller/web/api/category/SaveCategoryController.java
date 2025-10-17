package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.category;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CategoryMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.AddCategoryToCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.UpdateCategoryImageInCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.CategoryPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CategoryResponse;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.ImageStorageGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.ImageDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Salvar categoria.
 */
@Slf4j
public class SaveCategoryController {

	/**
	 * Salva uma categoria.
	 *
	 * @param categoryRequest
	 *            Categoria a ser salva
	 * @param imageFile
	 *            Arquivo de imagem para a categoria
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @param imageDataSource
	 *            Origem de dados para o gateway de imagens
	 * @return Categoria salva com identificadores atualizados
	 */
	public static CategoryResponse saveCategory(CategoryRequest categoryRequest, FileUploadDTO imageFile,
			CatalogDataSource catalogDataSource, ImageDataSource imageDataSource) {

		var gateway = new CatalogGateway(catalogDataSource);
		var imageStorageGateway = new ImageStorageGateway(imageDataSource);

		var categoryInput = CategoryMapper.toInput(categoryRequest);

		var catalog = AddCategoryToCatalogUseCase.addCategoryToCatalog(categoryInput, gateway);

		var savedCatalog = gateway.save(catalog);

		var savedCategory = savedCatalog.getLastCategoryOfCatalog();

		log.debug("Categoria criada com sucesso: {}", savedCategory.getId());

		if (imageFile != null) {
			var updatedCatalog = UpdateCategoryImageInCatalogUseCase.updateCategoryImageInCatalog(
					savedCategory.getCatalog().getId(), savedCategory.getId(), imageFile, gateway, imageStorageGateway);
			savedCatalog = gateway.save(updatedCatalog);
			savedCategory = savedCatalog.getLastCategoryOfCatalog();

			log.debug("Categoria atualizada com sucesso: {}", savedCategory.getImageUrl().imageUrl());
		}

		return CategoryPresenter.toCategoryResponse(savedCategory);
	}
}
