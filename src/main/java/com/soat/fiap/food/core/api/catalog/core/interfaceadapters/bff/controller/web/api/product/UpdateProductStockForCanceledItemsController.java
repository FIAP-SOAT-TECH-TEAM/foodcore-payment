package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.product;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductStockUpdateInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.ProductStockUpdateMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.UpdateProductStockForCanceledItemUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.OrderCanceledEventDto;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar quantidade em estoque de produtos de acordo com a
 * quantidade cancelada em um pedido.
 */
@Slf4j
public class UpdateProductStockForCanceledItemsController {
	/**
	 * Atualiza quantidade em estoque de produtos de acordo com a quantidade
	 * cancelada em um pedido.
	 *
	 * @param orderCanceledEvent
	 *            evento de cancelamento de pedido
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 */
	public static void updateProductStockForCanceledItems(OrderCanceledEventDto orderCanceledEvent,
			CatalogDataSource catalogDataSource) {
		var catalogGateway = new CatalogGateway(catalogDataSource);
		var productStockUpdateInput = ProductStockUpdateMapper.toInputFromCanceled(orderCanceledEvent.getItems());

		for (ProductStockUpdateInput.ProductStockItemInput productStockItemInput : productStockUpdateInput.items()) {

			var catalog = UpdateProductStockForCanceledItemUseCase.updateStockForCanceledItem(productStockItemInput,
					catalogGateway);

			catalogGateway.save(catalog);

			log.info("Atualização de quantidade em estoque realizada com sucesso! ProductId {}",
					productStockItemInput.productId());
		}
	}
}
