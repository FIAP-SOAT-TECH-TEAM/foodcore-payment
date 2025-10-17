package com.soat.fiap.food.core.api.order.infrastructure.out.payment.exceptions;

import com.soat.fiap.food.core.api.shared.infrastructure.out.exceptions.APIException;

/**
 * Exceção lançada quando existe um erro de retorno da API do microsserviço de
 * Pagamento
 */
public class PaymentException extends APIException {

	public PaymentException(String message, int statusCode) {
		super(message, statusCode);
	}

	public PaymentException(String message, Throwable cause, int statusCode) {
		super(message, cause, statusCode);
	}
}
