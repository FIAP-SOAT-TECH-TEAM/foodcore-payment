package com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio StockDebitEvent.
 * Serve como objeto de transferência entre o domínio e o mundo externo
 * (DataSource).
 */
@Data
public class StockDebitEventDto {
	public Long orderId;
	public String orderNumber;
	public String userId;
	public BigDecimal totalAmount;
	public List<StockDebitItemEventDto> items;
}
