package com.soat.fiap.food.core.api.catalog.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceNotFoundException;

/**
 * Exceção lançada quando um catalogo não é encontrado
 */
public class CatalogNotFoundException extends ResourceNotFoundException {

	public CatalogNotFoundException(String message) {
		super(message);
	}

	public CatalogNotFoundException(String message, Long id) {
		super(message, id);
	}
}
