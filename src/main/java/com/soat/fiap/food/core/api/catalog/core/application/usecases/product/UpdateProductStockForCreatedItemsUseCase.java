package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductStockUpdateInput;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.ProductNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar quantidade em estoque de um produto de acordo com a
 * quantidade solicitada em um pedido.
 */
@Slf4j
public class UpdateProductStockForCreatedItemsUseCase {

	/**
	 * Atualiza quantidade em estoque de um produto de acordo com a quantidade
	 * solicitada em um pedido.
	 *
	 * @param productStockItemInput
	 *            item do pedido
	 * @param gateway
	 *            Gateway de catalogo para comunicação com o mundo exterior
	 * @return Catalogo com o estoque de produto atualizado
	 */
	public static Catalog updateStockForCreatedItem(ProductStockUpdateInput.ProductStockItemInput productStockItemInput,
			CatalogGateway gateway) {
		if (productStockItemInput == null) {
			throw new ProductNotFoundException(
					"Itens de pedido é nulo. Não é possível efetuar atualização de quantidade em estoque.");
		}

		var catalog = gateway.findByProductId(productStockItemInput.productId());
		if (catalog.isEmpty()) {
			throw new CatalogNotFoundException(
					"Catálogo do produto do item de pedido não encontrado. Não é possível atualizar quantidade em estoque.");
		}

		var currentProductQuantity = catalog.get().getProductStockQuantity(productStockItemInput.productId());
		var newProductQuantity = currentProductQuantity - productStockItemInput.quantity();

		log.info("Iniciando atualização de quantidade em estoque: ProductId {}, atual: {}, nova: {}",
				productStockItemInput.productId(), currentProductQuantity, newProductQuantity);

		catalog.get().updateProductStockQuantity(productStockItemInput.productId(), newProductQuantity);

		return catalog.get();
	}
}
