package com.soat.fiap.food.core.api.shared.core.domain.exceptions;

/**
 * Exceção para conflitos de recursos (ex: CPF duplicado)
 */
public class ResourceConflictException extends RuntimeException {

	public ResourceConflictException(String message) {
		super(message);
	}

	public ResourceConflictException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("Já existe um(a) %s com %s: %s", resourceName, fieldName, fieldValue));
	}
}
