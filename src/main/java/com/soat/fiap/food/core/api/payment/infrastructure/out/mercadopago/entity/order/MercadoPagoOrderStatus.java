package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.order;

/**
 * DTO para o status de um pedido no adquirente
 */
public enum MercadoPagoOrderStatus {
	PAID("Paga"), PAYMENT_REQUIRED("Pagamento requerido");

	private final String description;

	MercadoPagoOrderStatus(String description) {
		this.description = description;
	}

	public static MercadoPagoOrderStatus fromValue(String value) {

		String normalized = value.trim().toUpperCase().replace("-", "_").replace(" ", "_");

		for (MercadoPagoOrderStatus method : values()) {
			if (method.name().equalsIgnoreCase(normalized)) {
				return method;
			}
		}

		throw new IllegalArgumentException("AcquirerOrderStatus desconhecido: " + value);
	}
}
