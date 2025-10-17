package com.soat.fiap.food.core.api.payment.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.BusinessException;

/**
 * Exceção lançada quando uma regra de negócio é violada na entidade pedido
 */
public class PaymentException extends BusinessException {

	public PaymentException(String message) {
		super(message);
	}

	public PaymentException(String message, Throwable cause) {
		super(message, cause);
	}
}
