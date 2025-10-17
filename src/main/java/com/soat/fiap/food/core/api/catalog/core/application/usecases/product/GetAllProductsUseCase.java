package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter todos produtos.
 */
@Slf4j
public class GetAllProductsUseCase {

	/**
	 * Lista todos os produtos de uma categoria.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Lista de produtos
	 */
	public static List<Product> getAllProducts(Long catalogId, Long categoryId, CatalogGateway gateway) {
		var catalog = gateway.findById(catalogId);

		if (catalog.isEmpty()) {
			log.warn("Catalogo não encontrado. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		}

		var products = catalog.get().getProductsFromCategoryById(categoryId);

		log.debug("Encontrados {} produtos", products.size());

		return products;
	}
}
