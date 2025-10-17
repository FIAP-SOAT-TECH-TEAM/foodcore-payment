package com.soat.fiap.food.core.api.catalog.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceConflictException;

/**
 * Exceção lançada quando um conflito referente a entidade categoria é
 * encontrada
 */
public class CategoryConflictException extends ResourceConflictException {

	public CategoryConflictException(String message) {
		super(message);
	}

	public CategoryConflictException(String resourceName, String fieldName, Object fieldValue) {
		super(resourceName, fieldName, fieldValue);
	}
}
