package com.soat.fiap.food.core.payment.payment.unit.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.payment.core.application.inputs.StockDebitInput;
import com.soat.fiap.food.core.payment.core.application.inputs.mappers.StockDebitMapper;
import com.soat.fiap.food.core.payment.order.core.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.payment.order.core.domain.events.OrderItemCreatedEvent;

@DisplayName("OrderCreatedMapper - Testes Unitários")
class OrderCreatedMapperTest {

	@Test @DisplayName("Deve mapear OrderCreatedEvent para OrderCreatedInput com sucesso")
	void shouldMapOrderCreatedEventToInput() {
		// Arrange
		OrderItemCreatedEvent item = new OrderItemCreatedEvent();
		item.setId(1L);
		item.setProductId(1L);
		item.setName("Lanche Teste");
		item.setQuantity(2);
		item.setUnitPrice(new BigDecimal("15.50"));
		item.setSubtotal(new BigDecimal("31.00"));
		item.setObservations("Sem cebola");

		OrderCreatedEvent event = new OrderCreatedEvent();
		event.setId(1L);
		event.setOrderNumber("ORD-001");
		event.setUserId("as23as3");
		event.setTotalAmount(new BigDecimal("31.00"));
		event.setItems(List.of(item));

		// Act
		StockDebitInput result = StockDebitMapper.toInput(event);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.orderId());
		assertEquals("ORD-001", result.orderNumber());
		assertEquals("as23as3", result.userId());
		assertEquals(new BigDecimal("31.00"), result.totalAmount());
		assertEquals(1, result.items().size());

		StockDebitInput.OrderCreatedItemInput mappedItem = result.items().get(0);
		assertEquals(1L, mappedItem.productId());
		assertEquals("Lanche Teste", mappedItem.name());
		assertEquals(2, mappedItem.quantity());
		assertEquals(new BigDecimal("15.50"), mappedItem.unitPrice());
		assertEquals(new BigDecimal("31.00"), mappedItem.subtotal());
		assertEquals("Sem cebola", mappedItem.observations());
	}

	@Test @DisplayName("Deve mapear OrderCreatedEvent com lista vazia de itens")
	void shouldMapOrderCreatedEventWithEmptyItems() {
		// Arrange
		OrderCreatedEvent event = new OrderCreatedEvent();
		event.setId(2L);
		event.setOrderNumber("ORD-002");
		event.setUserId("3s23as3");
		event.setTotalAmount(BigDecimal.ZERO);
		event.setItems(List.of());

		// Act
		StockDebitInput result = StockDebitMapper.toInput(event);

		// Assert
		assertNotNull(result);
		assertEquals(2L, result.orderId());
		assertEquals("ORD-002", result.orderNumber());
		assertEquals("3s23as3", result.userId());
		assertEquals(BigDecimal.ZERO, result.totalAmount());
		assertTrue(result.items().isEmpty());
	}

	@Test @DisplayName("Deve mapear OrderCreatedEvent com múltiplos itens")
	void shouldMapOrderCreatedEventWithMultipleItems() {
		// Arrange
		OrderItemCreatedEvent item1 = new OrderItemCreatedEvent();
		item1.setId(1L);
		item1.setProductId(1L);
		item1.setName("Lanche");
		item1.setQuantity(1);
		item1.setUnitPrice(new BigDecimal("20.00"));
		item1.setSubtotal(new BigDecimal("20.00"));
		item1.setObservations(null);

		OrderItemCreatedEvent item2 = new OrderItemCreatedEvent();
		item2.setId(2L);
		item2.setProductId(2L);
		item2.setName("Bebida");
		item2.setQuantity(2);
		item2.setUnitPrice(new BigDecimal("5.00"));
		item2.setSubtotal(new BigDecimal("10.00"));
		item2.setObservations("Gelada");

		OrderCreatedEvent event = new OrderCreatedEvent();
		event.setId(3L);
		event.setOrderNumber("ORD-003");
		event.setUserId("as25as3");
		event.setTotalAmount(new BigDecimal("30.00"));
		event.setItems(List.of(item1, item2));

		// Act
		StockDebitInput result = StockDebitMapper.toInput(event);

		// Assert
		assertNotNull(result);
		assertEquals(3L, result.orderId());
		assertEquals(2, result.items().size());

		StockDebitInput.OrderCreatedItemInput mappedItem1 = result.items().get(0);
		assertEquals(1L, mappedItem1.productId());
		assertEquals("Lanche", mappedItem1.name());
		assertNull(mappedItem1.observations());

		StockDebitInput.OrderCreatedItemInput mappedItem2 = result.items().get(1);
		assertEquals(2L, mappedItem2.productId());
		assertEquals("Bebida", mappedItem2.name());
		assertEquals("Gelada", mappedItem2.observations());
	}
}
