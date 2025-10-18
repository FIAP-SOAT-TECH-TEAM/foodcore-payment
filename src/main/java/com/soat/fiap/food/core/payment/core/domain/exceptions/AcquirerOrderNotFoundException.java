package com.soat.fiap.food.core.payment.core.domain.exceptions;

import com.soat.fiap.food.core.shared.core.domain.exceptions.ResourceNotFoundException;

/**
 * Exceção lançada quando um pedido no adquirente não é encontrado
 */
public class AcquirerOrderNotFoundException extends ResourceNotFoundException {

	public AcquirerOrderNotFoundException(String message) {
		super(message);
	}

	public AcquirerOrderNotFoundException(String message, Long id) {
		super(message, id);
	}
}
