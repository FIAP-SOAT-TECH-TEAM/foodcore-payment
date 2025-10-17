package com.soat.fiap.food.core.api.catalog.core.application.inputs;

import java.util.List;

/**
 * Representa um DTO de entrada da aplicação (Application Layer) contendo os
 * dados necessários para atualizar o estoque de produtos.
 * <p>
 * Esse input é utilizado no caso de uso de atualização de estoque após a
 * criação de itens de pedido.
 *
 * @param items
 *            Lista de itens com dados de produto e quantidade a ser descontada.
 */
public record ProductStockUpdateInput(List<ProductStockItemInput> items) {

	/**
	 * Construtor para inicializar um {@code ProductStockUpdateInput} com os itens
	 * de atualização de estoque.
	 *
	 * @param items
	 *            Lista de produtos e suas respectivas quantidades.
	 */
	public ProductStockUpdateInput {
	}

	/**
	 * Representa um item individual para atualização de estoque de um produto.
	 *
	 * @param productId
	 *            ID do produto.
	 * @param quantity
	 *            Quantidade a ser descontada do estoque.
	 */
	public record ProductStockItemInput(Long productId, Integer quantity) {

		/**
		 * Construtor para inicializar um {@code ProductStockItemInput}.
		 *
		 * @param productId
		 *            ID do produto.
		 * @param quantity
		 *            Quantidade solicitada.
		 */
		public ProductStockItemInput {
		}
	}
}
