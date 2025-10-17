package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * Representa a entidade de geração de um QrCode na API do mercado pago.
 */
@Data
public class MercadoPagoGenerateQrCodeEntity {
	private String external_reference;
	private String title;
	private String description;
	private String notification_url;
	private BigDecimal total_amount;
	private String expiration_date;
	private List<MercadoPagoGenerateQrCodeItemEntity> items;
}
