package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.ProductNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.ProductGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter produtos por identificadores.
 */
@Slf4j
public class GetProductsByIdsUseCase {

	/**
	 * Busca produtos por IDs.
	 *
	 * @param productIds
	 *            IDs do produtos
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Produtos encontrados
	 */
	public static List<Product> getProductsByIds(List<Long> productIds, ProductGateway gateway) {
		var products = gateway.findByProductIds(productIds);

		if (products.isEmpty() || products.get().isEmpty()) {
			log.warn("Nenhum produto encontrado na tentativa de recuperar produtos pelos IDs: {}", productIds);
			throw new ProductNotFoundException("Nenhum produto encontrado para a lista de IDs de produtos fornecida");
		} else if (products.get().size() != productIds.size()) {
			var foundIds = products.get().stream().map(Product::getId).toList();

			var missingId = productIds.stream().filter(id -> !foundIds.contains(id)).findFirst().orElse(null);

			log.warn("Produto com ID {} não encontrado no catálogo.", missingId);

			throw new ProductNotFoundException("Produto", missingId);
		}

		return products.get();
	}
}
