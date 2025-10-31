package com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events;

import java.math.BigDecimal;

import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio
 * StockDebitItemEvent. Serve como objeto de transferência entre o domínio e o
 * mundo externo (DataSource).
 */
@Data
public class StockDebitItemEventDto {
	private Long productId;
	private String name;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal subtotal;
	private String observations;
}
