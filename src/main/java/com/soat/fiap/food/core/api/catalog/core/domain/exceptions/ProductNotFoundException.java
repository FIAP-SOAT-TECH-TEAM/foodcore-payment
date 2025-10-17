package com.soat.fiap.food.core.api.catalog.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceNotFoundException;

/**
 * Exceção lançada quando um produto não é encontrado
 */
public class ProductNotFoundException extends ResourceNotFoundException {

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(String message, Long id) {
		super(message, id);
	}
}
