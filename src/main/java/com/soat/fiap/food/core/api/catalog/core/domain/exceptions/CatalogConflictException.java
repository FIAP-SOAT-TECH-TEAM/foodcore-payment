package com.soat.fiap.food.core.api.catalog.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceConflictException;

/**
 * Exceção lançada quando um conflito referente a entidade catalogo é encontrada
 */
public class CatalogConflictException extends ResourceConflictException {

	public CatalogConflictException(String message) {
		super(message);
	}

	public CatalogConflictException(String resourceName, String fieldName, Object fieldValue) {
		super(resourceName, fieldName, fieldValue);
	}
}
