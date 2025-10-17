package com.soat.fiap.food.core.api.order.infrastructure.common.source;

import java.util.List;

import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.product.ProductDTO;

/**
 * DataSource para obtenção de Produto.
 */
public interface ProductDataSource {

	/**
	 * Retorna uma lista de Produtos por IDs
	 *
	 * @param productIds
	 *            IDs do produtos
	 */
	List<ProductDTO> findByProductIds(List<Long> productIds);

}
