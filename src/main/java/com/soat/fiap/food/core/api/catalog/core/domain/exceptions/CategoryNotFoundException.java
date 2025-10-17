package com.soat.fiap.food.core.api.catalog.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceNotFoundException;

/**
 * Exceção lançada quando uma categoria não é encontrada
 */
public class CategoryNotFoundException extends ResourceNotFoundException {

	public CategoryNotFoundException(String message) {
		super(message);
	}

	public CategoryNotFoundException(String message, Long id) {
		super(message, id);
	}
}
