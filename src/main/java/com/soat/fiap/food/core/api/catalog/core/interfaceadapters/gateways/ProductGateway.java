package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways;

import java.util.List;
import java.util.Optional;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers.ProductDTOMapper;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.ProductDataSource;

/**
 * Gateway para obtenção de Produto.
 */
public class ProductGateway {

	private final ProductDataSource productDataSource;

	public ProductGateway(ProductDataSource productDataSource) {
		this.productDataSource = productDataSource;
	}

	/**
	 * Retorna uma lista de produtos por IDs de produtos
	 *
	 * @param productIds
	 *            IDs do produtos
	 */
	public Optional<List<Product>> findByProductIds(List<Long> productIds) {
		return productDataSource.findByProductIds(productIds).map(ProductDTOMapper::toDomainList);
	}
}
