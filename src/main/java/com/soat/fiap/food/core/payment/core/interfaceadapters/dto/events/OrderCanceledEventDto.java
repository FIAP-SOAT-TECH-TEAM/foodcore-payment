package com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events;

import java.util.List;

import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio OrderCanceledEvent.
 * Serve como objeto de transferência entre o domínio e o mundo externo
 * (DataSource).
 */
@Data
public class OrderCanceledEventDto {
	public Long id;
	public List<OrderItemCanceledEventDto> items;
}
