package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.product;

import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.GetProductsByIdsUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.ProductPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.ProductGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.ProductDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.ProductResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Buscar produtos por uma lista de IDs.
 */
@Slf4j
public class GetProductsByIdsController {

	/**
	 * Busca produtos por IDs.
	 *
	 * @param productIds
	 *            ID dos produtos
	 * @param productDataSource
	 *            Origem de dados para o gateway
	 * @return os produtos encontrados
	 */
	public static List<ProductResponse> getProductsByIds(List<Long> productIds, ProductDataSource productDataSource) {
		log.debug("Buscando produstos pela lista de IDs {}", productIds);

		var gateway = new ProductGateway(productDataSource);

		var existingProduct = GetProductsByIdsUseCase.getProductsByIds(productIds, gateway);

		return ProductPresenter.toListProductResponse(existingProduct);
	}
}
