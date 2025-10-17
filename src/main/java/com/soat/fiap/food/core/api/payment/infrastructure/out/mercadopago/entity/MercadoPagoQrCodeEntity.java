package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity;

import lombok.Data;

/**
 * Representa a geração de um QrCode na API do mercado pago
 */
@Data
public class MercadoPagoQrCodeEntity {
	private String in_store_order_id;
	private String qr_data;
}
