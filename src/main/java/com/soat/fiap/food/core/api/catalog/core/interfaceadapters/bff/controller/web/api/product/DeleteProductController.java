package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.product;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.RemoveProductFromCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Excluir produto.
 */
@Slf4j
public class DeleteProductController {

	/**
	 * Exclui um produto de uma categoria específica dentro de um catálogo.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @param productId
	 *            ID do produto a ser removido
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 */
	public static void deleteProduct(Long catalogId, Long categoryId, Long productId,
			CatalogDataSource catalogDataSource) {
		log.debug("Excluindo produto de id: {} da categoria de id: {} do catalogo de id: {}", productId, categoryId,
				catalogId);

		var gateway = new CatalogGateway(catalogDataSource);

		var catalogWithoutProduct = RemoveProductFromCategoryUseCase.removeProductFromCategory(catalogId, categoryId,
				productId, gateway);

		gateway.save(catalogWithoutProduct);

		log.debug("Produto excluído com sucesso: {}", productId);
	}
}
