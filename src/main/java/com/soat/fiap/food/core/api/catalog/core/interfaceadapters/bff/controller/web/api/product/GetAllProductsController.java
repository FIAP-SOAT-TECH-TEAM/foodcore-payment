package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.product;

import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.GetAllProductsUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.ProductPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.ProductResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Obter todos os produtos.
 */
@Slf4j
public class GetAllProductsController {

	/**
	 * Obtém todos os produtos.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @return Produtos encontrados
	 */
	public static List<ProductResponse> getAllProducts(Long catalogId, Long categoryId,
			CatalogDataSource catalogDataSource) {
		log.debug("Buscando todos os produtos da categoria de id: {}", categoryId);

		var gateway = new CatalogGateway(catalogDataSource);

		var existingProducts = GetAllProductsUseCase.getAllProducts(catalogId, categoryId, gateway);

		return ProductPresenter.toListProductResponse(existingProducts);
	}
}
