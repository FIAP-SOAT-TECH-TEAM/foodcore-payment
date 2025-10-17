package com.soat.fiap.food.core.api.order.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.api.order.core.application.inputs.mappers.CreateOrderMapper;
import com.soat.fiap.food.core.api.order.core.application.usecases.*;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.bff.presenter.web.api.OrderPresenter;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways.OrderGateway;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways.ProductGateway;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.ProductDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderResponse;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.AuthenticatedUserGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AuthenticatedUserSource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Salvar pedido.
 */
@Slf4j
public class SaveOrderController {

	/**
	 * Salva um pedido.
	 *
	 * @param createOrderRequest
	 *            Pedido a ser salvo
	 * @param orderDataSource
	 *            Origem de dados para o gateway de pedido
	 * @param productDataSource
	 *            Origem de dados para o gateway de produto
	 * @param authenticatedUserSource
	 *            Origem de dados para o gateway de usuário autenticado
	 * @param eventPublisherSource
	 *            Origem de publicação de eventos
	 * @return Pedido salvo com identificadores atualizados
	 */
	public static OrderResponse saveOrder(CreateOrderRequest createOrderRequest, OrderDataSource orderDataSource,
			ProductDataSource productDataSource, AuthenticatedUserSource authenticatedUserSource,
			EventPublisherSource eventPublisherSource) {

		var orderGateway = new OrderGateway(orderDataSource);
		var productGateway = new ProductGateway(productDataSource);
		var eventPublisherGateway = new EventPublisherGateway(eventPublisherSource);
		var authenticatedUserGateway = new AuthenticatedUserGateway((authenticatedUserSource));

		var orderInput = CreateOrderMapper.toInput(createOrderRequest, authenticatedUserGateway);
		var order = CreateOrderUseCase.createOrder(orderInput);

		EnsureValidOrderItemsUseCase.ensureValidOrderItems(order.getOrderItems(), productGateway);
		ApplyDiscountUseCase.applyDiscount(order, authenticatedUserGateway);

		var savedOrder = orderGateway.save(order);

		PublishOrderCreatedEventUseCase.publishCreateOrderEvent(savedOrder, eventPublisherGateway);

		var saveOrderToResponse = OrderPresenter.toOrderResponse(savedOrder);

		log.info("Pedido {} criado com sucesso. Total: {}", savedOrder.getId(), savedOrder.getAmount());

		return saveOrderToResponse;
	}
}
