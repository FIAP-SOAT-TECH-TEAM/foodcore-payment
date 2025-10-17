package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Remover produto de uma categoria.
 */
@Slf4j
public class RemoveProductFromCategoryUseCase {

	/**
	 * Remove um produto de uma categoria dentro de um catálogo.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @param productId
	 *            ID do produto a ser removido
	 * @param gateway
	 *            Gateway para acesso ao catálogo
	 * @return Catálogo atualizado com o produto removido da categoria
	 */
	public static Catalog removeProductFromCategory(Long catalogId, Long categoryId, Long productId,
			CatalogGateway gateway) {
		var catalog = gateway.findById(catalogId);

		if (catalog.isEmpty()) {
			log.warn("Tentativa de excluir produto com catálogo inexistente. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		}

		catalog.get().removeProductFromCategory(categoryId, productId);

		return catalog.get();
	}
}
