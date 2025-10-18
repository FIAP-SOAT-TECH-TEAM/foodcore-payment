package com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio OrderCreatedEvent.
 * Serve como objeto de transferência entre o domínio e o mundo externo
 * (DataSource).
 */
@Data
public class OrderCreatedEventDto {
	public Long id;
	public String orderNumber;
	public String statusDescription;
	public String userId;
	public BigDecimal totalAmount;
	public List<OrderItemCreatedEventDto> items;
}
