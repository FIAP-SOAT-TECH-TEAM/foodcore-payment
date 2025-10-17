package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter produto por identificador.
 */
@Slf4j
public class GetProductByIdUseCase {

	/**
	 * Busca um produto por ID dentro de uma categoria.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @param productId
	 *            ID do produto
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Produto encontrado
	 */
	public static Product getProductById(Long catalogId, Long categoryId, Long productId, CatalogGateway gateway) {
		var catalog = gateway.findById(catalogId);

		if (catalog.isEmpty()) {
			log.warn("Catalogo não encontrado. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		}

		return catalog.get().getProductFromCategoryById(categoryId, productId);
	}
}
