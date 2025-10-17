package com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways;

import java.util.List;

import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.product.ProductDTO;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.ProductDataSource;

/**
 * Gateway para comunicação com o microsserviço de catálogo (Product)
 */
public class ProductGateway {

	private final ProductDataSource productDataSource;

	public ProductGateway(ProductDataSource productDataSource) {
		this.productDataSource = productDataSource;
	}

	/**
	 * Retorna uma lista de Produtos por IDs
	 *
	 * @param productIds
	 *            IDs do produtos
	 */
	public List<ProductDTO> findByProductIds(List<Long> productIds) {
		return productDataSource.findByProductIds(productIds);
	}

}
