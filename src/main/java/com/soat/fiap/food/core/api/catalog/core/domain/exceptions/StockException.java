package com.soat.fiap.food.core.api.catalog.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.BusinessException;

/**
 * Exceção lançada quando uma regra de negócio referente a entidade estoque é
 * violada
 */
public class StockException extends BusinessException {

	public StockException(String message) {
		super(message);
	}

	public StockException(String message, Throwable cause) {
		super(message, cause);
	}
}
