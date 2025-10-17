package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.ProductMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar produto.
 */
@Slf4j
public class UpdateProductInCategoryUseCase {

	/**
	 * Atualiza um produto.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @param productId
	 *            ID do produto a ser atualizado
	 * @param productInput
	 *            Produto a ser atualizado
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Catalogo com o produto atualizado
	 */
	public static Catalog updateProductInCategory(Long catalogId, Long categoryId, Long productId,
			ProductInput productInput, CatalogGateway gateway) {
		var catalog = gateway.findById(catalogId);
		var product = ProductMapper.toDomain(productInput);
		product.setId(productId);

		log.debug("Atualizando produto: {}", productId);

		if (catalog.isEmpty()) {
			log.warn("Tentativa de atualizar produto com catálogo inexistente. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		} else if (!productInput.categoryId().equals(categoryId)) {

			catalog.get().moveCategoryProduct(categoryId, productInput.categoryId(), productId);

			log.debug("Produto movido com sucesso para categoria: {}", productInput.categoryId());
		}

		catalog.get().updateProductInCategory(productInput.categoryId(), product);

		return catalog.get();
	}
}
