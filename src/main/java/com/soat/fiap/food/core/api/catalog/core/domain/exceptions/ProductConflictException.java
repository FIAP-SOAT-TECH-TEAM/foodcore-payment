package com.soat.fiap.food.core.api.catalog.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceConflictException;

/**
 * Exceção lançada quando um conflito referente a entidade produto é encontrada
 */
public class ProductConflictException extends ResourceConflictException {

	public ProductConflictException(String message) {
		super(message);
	}

	public ProductConflictException(String resourceName, String fieldName, Object fieldValue) {
		super(resourceName, fieldName, fieldValue);
	}
}
