package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.product;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.UpdateProductImageInCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.ProductPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.ProductResponse;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.ImageStorageGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.ImageDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar imagem do produto.
 */
@Slf4j
public class UpdateProductImageController {

	/**
	 * Atualiza apenas a imagem de um produto existente.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria do produto
	 * @param productId
	 *            ID do produto a ter a imagem atualizada
	 * @param imageFile
	 *            Arquivo da nova imagem do produto
	 * @param catalogDataSource
	 *            Origem de dados para o gateway do catálogo
	 * @param imageDataSource
	 *            Origem de dados para o gateway de armazenamento de imagem
	 * @return Produto com a imagem atualizada
	 * @throws CatalogNotFoundException
	 *             se o catálogo não for encontrado
	 * @throws IllegalArgumentException
	 *             se o arquivo de imagem for nulo ou vazio
	 * @throws RuntimeException
	 *             se ocorrer um erro durante o upload da imagem
	 */
	public static ProductResponse updateProductImage(Long catalogId, Long categoryId, Long productId,
			FileUploadDTO imageFile, CatalogDataSource catalogDataSource, ImageDataSource imageDataSource) {

		log.debug("Atualizando imagem do produto ID: {}", productId);

		CatalogGateway catalogGateway = new CatalogGateway(catalogDataSource);
		ImageStorageGateway imageStorageGateway = new ImageStorageGateway(imageDataSource);

		var catalog = UpdateProductImageInCategoryUseCase.updateProductImageInCategory(catalogId, categoryId, productId,
				imageFile, catalogGateway, imageStorageGateway);

		var savedCatalog = catalogGateway.save(catalog);
		var savedProduct = savedCatalog.getProductById(productId);

		return ProductPresenter.toProductResponse(savedProduct);
	}
}
