package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.product;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.ProductMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.UpdateProductInCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.ProductPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.ProductRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.ProductResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar produto.
 */
@Slf4j
public class UpdateProductController {

	/**
	 * Atualiza um produto.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @param productId
	 *            ID do produto a ser atualizado
	 * @param productRequest
	 *            Produto a ser atualizada
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @return Produto atualizado com identificadores atualizados
	 */
	public static ProductResponse updateProduct(Long catalogId, Long categoryId, Long productId,
			ProductRequest productRequest, CatalogDataSource catalogDataSource) {

		var gateway = new CatalogGateway(catalogDataSource);

		var productInput = ProductMapper.toInput(productRequest);

		var catalog = UpdateProductInCategoryUseCase.updateProductInCategory(catalogId, categoryId, productId,
				productInput, gateway);

		var updatedCatalog = gateway.save(catalog);

		var updatedProduct = updatedCatalog.getProductFromCategoryById(productRequest.getCategoryId(), productId);

		log.debug("Produto atualizado com sucesso: {}", productId);

		return ProductPresenter.toProductResponse(updatedProduct);
	}
}
