package com.soat.fiap.food.core.api.catalog.infrastructure.common.source;

import java.util.List;
import java.util.Optional;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;

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
	Optional<List<ProductDTO>> findByProductIds(List<Long> productIds);

}
