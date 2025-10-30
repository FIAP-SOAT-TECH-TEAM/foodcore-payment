package com.soat.fiap.food.core.payment.core.domain.exceptions;

import com.soat.fiap.food.core.shared.core.domain.exceptions.BusinessException;

/**
 * Exceção lançada quando um pagamento já foi processado
 */
public class PaymentAlreadyProcessedException extends BusinessException {

	public PaymentAlreadyProcessedException(String message) {
		super(message);
	}

	public PaymentAlreadyProcessedException(String message, Throwable cause) {
		super(message, cause);
	}
}
