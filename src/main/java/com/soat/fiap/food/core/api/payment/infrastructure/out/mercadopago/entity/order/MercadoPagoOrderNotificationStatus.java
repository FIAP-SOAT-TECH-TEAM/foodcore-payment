package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.order;

/**
 * Representa o status de notificação de um pedido na API do mercado pago.
 */
public enum MercadoPagoOrderNotificationStatus {
	OPENED("Aberta"), CLOSED("Fechada");

	private final String description;

	MercadoPagoOrderNotificationStatus(String description) {
		this.description = description;
	}

	public static MercadoPagoOrderNotificationStatus fromValue(String value) {

		String normalized = value.trim().toUpperCase().replace("-", "_").replace(" ", "_");

		for (MercadoPagoOrderNotificationStatus method : values()) {
			if (method.name().equalsIgnoreCase(normalized)) {
				return method;
			}
		}

		throw new IllegalArgumentException("AcquirerOrderNotificationStatus desconhecido: " + value);
	}
}
