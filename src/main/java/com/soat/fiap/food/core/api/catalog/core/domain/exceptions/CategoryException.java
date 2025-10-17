package com.soat.fiap.food.core.api.catalog.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.BusinessException;

/**
 * Exceção lançada quando uma regra de negócio referente a entidade categoria é
 * violada
 */
public class CategoryException extends BusinessException {

	public CategoryException(String message) {
		super(message);
	}

	public CategoryException(String message, Throwable cause) {
		super(message, cause);
	}
}
