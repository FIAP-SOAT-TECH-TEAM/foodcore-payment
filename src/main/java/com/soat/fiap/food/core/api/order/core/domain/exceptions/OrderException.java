package com.soat.fiap.food.core.api.order.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.BusinessException;

/**
 * Exceção lançada quando uma regra de negócio é violada na entidade pedido
 */
public class OrderException extends BusinessException {

	public OrderException(String message) {
		super(message);
	}

	public OrderException(String message, Throwable cause) {
		super(message, cause);
	}
}
