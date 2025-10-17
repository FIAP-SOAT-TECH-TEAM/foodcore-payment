package com.soat.fiap.food.core.api.shared.fixtures;

import java.math.BigDecimal;
import java.util.List;

import com.soat.fiap.food.core.api.order.core.application.inputs.CreateOrderInput;
import com.soat.fiap.food.core.api.order.core.application.inputs.OrderItemInput;
import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.core.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderItemPrice;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;

/**
 * Fixture para criação de objetos do módulo Order para testes unitários
 */
public class OrderFixture {

	public static Order createValidOrder() {
		var orderItems = List.of(createValidOrderItem());
		return new Order("A23basb3u123", orderItems);
	}

	public static Order createOrderWithMultipleItems() {
		var orderItems = List.of(createValidOrderItem(), createBeverageOrderItem(), createDessertOrderItem());
		return new Order("A23basb3u123", orderItems);
	}

	public static Order createOrderWithoutUser() {
		var orderItems = List.of(createValidOrderItem());
		return new Order(null, orderItems);
	}

	public static Order createOrderWithStatus(OrderStatus status) {
		var order = createValidOrder();
		order.setOrderStatus(status);
		return order;
	}

	public static Order createCompletedOrder() {
		var order = createValidOrder();
		order.setId(1L);
		order.setOrderStatus(OrderStatus.COMPLETED);
		return order;
	}

	public static Order createPreparingOrder() {
		var order = createValidOrder();
		order.setId(2L);
		order.setOrderStatus(OrderStatus.PREPARING);
		return order;
	}

	public static Order createReadyOrder() {
		var order = createValidOrder();
		order.setId(3L);
		order.setOrderStatus(OrderStatus.READY);
		return order;
	}

	public static OrderItem createValidOrderItem() {
		return new OrderItem(1L, // productId
				"Big Mac", new OrderItemPrice(2, new BigDecimal("25.90")), null);
	}

	public static OrderItem createBeverageOrderItem() {
		return new OrderItem(2L, "Coca-Cola", new OrderItemPrice(1, new BigDecimal("8.50")), "Sem gelo");
	}

	public static OrderItem createDessertOrderItem() {
		return new OrderItem(3L, "Torta de Chocolate", new OrderItemPrice(1, new BigDecimal("18.90")), "Aquecida");
	}

	public static OrderItem createExpensiveOrderItem() {
		return new OrderItem(4L, "Combo Premium", new OrderItemPrice(1, new BigDecimal("45.00")), null);
	}

	public static OrderItem createOrderItemWithObservations() {
		return new OrderItem(2L, "Batata Frita G", new OrderItemPrice(1, new BigDecimal("15.00")), "Sem sal");
	}

	public static Order createOrderWithSingleItem() {
		var orderItems = List.of(createValidOrderItem());
		return new Order("A23basb3u123", orderItems);
	}

	// Input DTOs para testes
	public static CreateOrderInput createValidCreateOrderInput() {
		var orderItems = List.of(createValidOrderItemInput());
		return new CreateOrderInput("A23basb3u123", orderItems);
	}

	public static CreateOrderInput createCreateOrderInputWithMultipleItems() {
		var orderItems = List.of(createValidOrderItemInput(), createBeverageOrderItemInput(),
				createDessertOrderItemInput());
		return new CreateOrderInput("A23basb3u123", orderItems);
	}

	public static CreateOrderInput createCreateOrderInputWithoutUser() {
		var orderItems = List.of(createValidOrderItemInput());
		return new CreateOrderInput(null, orderItems);
	}

	public static CreateOrderInput createCreateOrderInputWithObservations() {
		var orderItems = List.of(createOrderItemInputWithObservations());
		return new CreateOrderInput("A23basb3u123", orderItems);
	}

	public static OrderItemInput createValidOrderItemInput() {
		return new OrderItemInput(1L, "Big Mac", 2, new BigDecimal("25.90"), null);
	}

	public static OrderItemInput createBeverageOrderItemInput() {
		return new OrderItemInput(2L, "Coca-Cola", 1, new BigDecimal("8.50"), "Sem gelo");
	}

	public static OrderItemInput createDessertOrderItemInput() {
		return new OrderItemInput(3L, "Torta de Chocolate", 1, new BigDecimal("18.90"), "Aquecida");
	}

	public static OrderItemInput createOrderItemInputWithObservations() {
		return new OrderItemInput(1L, "Big Mac", 1, new BigDecimal("25.90"), "Sem cebola, extra queijo");
	}

	public static OrderItemInput createExpensiveOrderItemInput() {
		return new OrderItemInput(4L, "Combo Premium", 1, new BigDecimal("45.00"), null);
	}
}
