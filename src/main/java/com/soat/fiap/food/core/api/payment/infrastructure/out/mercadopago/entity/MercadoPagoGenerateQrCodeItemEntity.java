package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Representa a Entidade de itens do pedido na geração de um QrCode na API do
 * mercado pago.
 */
@Data
public class MercadoPagoGenerateQrCodeItemEntity {
	private String sku_number;
	private String category;
	private String title;
	private String description;
	private BigDecimal unit_price;
	private int quantity;
	private String unit_measure;
	private BigDecimal total_amount;
}
