package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.GetCatalogByIdUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("GetCatalogByIdUseCase - Testes Unitários")
class GetCatalogByIdUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve retornar catálogo quando encontrado por ID")
	void shouldReturnCatalogWhenFoundById() {
		// Arrange
		var catalog = CatalogFixture.createValidCatalog();
		catalog.setId(1L);

		when(catalogGateway.findById(1L)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetCatalogByIdUseCase.getCatalogById(1L, catalogGateway);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals(catalog.getName(), result.getName());
		assertEquals(catalog.getCategories(), result.getCategories());
	}

	@Test
	@DisplayName("Deve lançar exceção quando catálogo não for encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		when(catalogGateway.findById(999L)).thenReturn(Optional.empty());

		// Act & Assert
		var exception = assertThrows(CatalogNotFoundException.class,
				() -> GetCatalogByIdUseCase.getCatalogById(999L, catalogGateway));

		assertEquals("Catalogo não encontrado com id: 999", exception.getMessage());
	}

	@Test @DisplayName("Deve retornar catálogo com categorias quando encontrado")
	void shouldReturnCatalogWithCategoriesWhenFound() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithCategories();
		catalog.setId(2L);

		when(catalogGateway.findById(2L)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetCatalogByIdUseCase.getCatalogById(2L, catalogGateway);

		// Assert
		assertNotNull(result);
		assertEquals(2L, result.getId());
		assertFalse(result.getCategories().isEmpty());
		assertTrue(result.getCategories().size() > 0);
	}

	@Test @DisplayName("Deve retornar catálogo específico por ID válido")
	void shouldReturnSpecificCatalogByValidId() {
		// Arrange
		var catalogId = 42L;
		var catalog = CatalogFixture.createValidCatalog();
		catalog.setId(catalogId);
		catalog.setName("Catálogo Específico");

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = GetCatalogByIdUseCase.getCatalogById(catalogId, catalogGateway);

		// Assert
		assertNotNull(result);
		assertEquals(catalogId, result.getId());
		assertEquals("Catálogo Específico", result.getName());
	}
}
