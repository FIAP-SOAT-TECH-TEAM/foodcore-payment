package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.category;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.UpdateCategoryImageInCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.CategoryPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CategoryResponse;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.ImageStorageGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.ImageDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar imagem da categoria.
 */
@Slf4j
public class UpdateCategoryImageController {
	/**
	 * Atualiza apenas a imagem de uma categoria existente.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria do categoria
	 * @param imageFile
	 *            Arquivo da nova imagem
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @param imageDataSource
	 *            Origem de dados de imagem para o gateway
	 * @throws CatalogNotFoundException
	 *             se o catálogo não for encontrado
	 * @throws IllegalArgumentException
	 *             se o arquivo de imagem for nulo ou vazio
	 * @throws RuntimeException
	 *             se ocorrer um erro durante o upload da imagem
	 */
	public static CategoryResponse updateProductImage(Long catalogId, Long categoryId, FileUploadDTO imageFile,
			CatalogDataSource catalogDataSource, ImageDataSource imageDataSource) {

		log.debug("Atualizando imagem do categoria ID: {}", categoryId);

		var catalogGateway = new CatalogGateway(catalogDataSource);
		var imageStorageGateway = new ImageStorageGateway(imageDataSource);

		var catalog = UpdateCategoryImageInCatalogUseCase.updateCategoryImageInCatalog(catalogId, categoryId, imageFile,
				catalogGateway, imageStorageGateway);

		var savedCatalog = catalogGateway.save(catalog);
		var savedCategory = savedCatalog.getCategoryById(categoryId);

		return CategoryPresenter.toCategoryResponse(savedCategory);
	}
}
