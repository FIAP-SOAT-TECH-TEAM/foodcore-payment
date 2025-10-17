package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.product;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductStockUpdateInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.ProductStockUpdateMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.UpdateProductStockForCreatedItemsUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.OrderCreatedEventDto;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar quantidade em estoque de produtos de acordo com a
 * quantidade solicitada em um pedido.
 */
@Slf4j
public class UpdateProductStockForCreatedItemsController {
	/**
	 * Atualiza quantidade em estoque de produtos de acordo com a quantidade
	 * solicitada em um pedido.
	 *
	 * @param orderCreatedEvent
	 *            evento de criação de pedido
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 */
	public static void updateProductStockForCreatedItems(OrderCreatedEventDto orderCreatedEvent,
			CatalogDataSource catalogDataSource) {
		var catalogGateway = new CatalogGateway(catalogDataSource);
		var productStockUpdateInput = ProductStockUpdateMapper.toInput(orderCreatedEvent.getItems());

		for (ProductStockUpdateInput.ProductStockItemInput productStockItemInput : productStockUpdateInput.items()) {

			var catalog = UpdateProductStockForCreatedItemsUseCase.updateStockForCreatedItem(productStockItemInput,
					catalogGateway);

			catalogGateway.save(catalog);

			log.info("Atualização de quantidade em estoque realizada com sucesso! ProductId {}",
					productStockItemInput.productId());
		}
	}
}
