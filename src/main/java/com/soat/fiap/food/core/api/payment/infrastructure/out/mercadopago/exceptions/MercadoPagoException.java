package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.exceptions;

import com.soat.fiap.food.core.api.shared.infrastructure.out.exceptions.APIException;

/**
 * Exceção lançada quando existe um erro de retorno da API do Mercado Pago
 */
public class MercadoPagoException extends APIException {

	public MercadoPagoException(String message, int statusCode) {
		super(message, statusCode);
	}

	public MercadoPagoException(String message, Throwable cause, int statusCode) {
		super(message, cause, statusCode);
	}
}
