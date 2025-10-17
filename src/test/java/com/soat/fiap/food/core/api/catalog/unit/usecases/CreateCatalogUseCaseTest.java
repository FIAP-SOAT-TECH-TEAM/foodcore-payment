package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CatalogInput;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.CreateCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("CreateCatalogUseCase - Testes Unitários")
class CreateCatalogUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve criar catálogo com sucesso")
	void shouldCreateCatalogSuccessfully() {
		// Arrange
		var catalogInput = new CatalogInput("Catálogo Principal");
		when(catalogGateway.existsByName(anyString())).thenReturn(false);

		// Act
		var result = CreateCatalogUseCase.createCatalog(catalogInput, catalogGateway);

		// Assert
		assertNotNull(result);
		assertEquals("Catálogo Principal", result.getName());
		assertNotNull(result.getCreatedAt());
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo já existe com mesmo nome")
	void shouldThrowExceptionWhenCatalogAlreadyExists() {
		// Arrange
		var catalogInput = new CatalogInput("Catálogo Existente");
		when(catalogGateway.existsByName("Catálogo Existente")).thenReturn(true);

		// Act & Assert
		var exception = assertThrows(CatalogConflictException.class,
				() -> CreateCatalogUseCase.createCatalog(catalogInput, catalogGateway));

		assertEquals("Já existe um(a) Catalogo com Nome: Catálogo Existente", exception.getMessage());
	}

	@Test @DisplayName("Deve criar catálogo com nome diferente quando não há conflito")
	void shouldCreateCatalogWithDifferentNameWhenNoConflict() {
		// Arrange
		var catalogInput = new CatalogInput("Novo Catálogo");
		when(catalogGateway.existsByName("Novo Catálogo")).thenReturn(false);

		// Act
		var result = CreateCatalogUseCase.createCatalog(catalogInput, catalogGateway);

		// Assert
		assertNotNull(result);
		assertEquals("Novo Catálogo", result.getName());
		assertTrue(result.getCategories().isEmpty());
	}
}
