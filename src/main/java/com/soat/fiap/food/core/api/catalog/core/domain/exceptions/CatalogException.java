package com.soat.fiap.food.core.api.catalog.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.BusinessException;

/**
 * Exceção lançada quando uma regra de negócio referente a entidade catalogo é
 * violada
 */
public class CatalogException extends BusinessException {

	public CatalogException(String message) {
		super(message);
	}

	public CatalogException(String message, Throwable cause) {
		super(message, cause);
	}
}
