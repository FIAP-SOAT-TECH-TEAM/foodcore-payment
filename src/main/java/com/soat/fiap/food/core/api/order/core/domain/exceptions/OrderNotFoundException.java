package com.soat.fiap.food.core.api.order.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceNotFoundException;

/**
 * Exceção lançada quando um pedido não é encontrado
 */
public class OrderNotFoundException extends ResourceNotFoundException {

	public OrderNotFoundException(String message) {
		super(message);
	}

	public OrderNotFoundException(String message, Long id) {
		super(message, id);
	}
}
