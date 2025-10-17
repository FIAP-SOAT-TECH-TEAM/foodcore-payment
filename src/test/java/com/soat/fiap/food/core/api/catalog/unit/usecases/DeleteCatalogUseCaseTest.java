package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.DeleteCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("DeleteCatalogUseCase - Testes Unitários")
class DeleteCatalogUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve deletar catálogo com sucesso")
	void shouldDeleteCatalogSuccessfully() {
		// Arrange
		Long catalogId = 1L;
		when(catalogGateway.existsById(catalogId)).thenReturn(true);
		when(catalogGateway.existsCategoryByCatalogId(catalogId)).thenReturn(false);

		// Act & Assert
		assertDoesNotThrow(() -> DeleteCatalogUseCase.deleteCatalog(catalogId, catalogGateway));

		verify(catalogGateway).existsById(catalogId);
		verify(catalogGateway).existsCategoryByCatalogId(catalogId);
		verify(catalogGateway).delete(catalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não existe")
	void shouldThrowExceptionWhenCatalogDoesNotExist() {
		// Arrange
		Long catalogId = 999L;
		when(catalogGateway.existsById(catalogId)).thenReturn(false);

		// Act & Assert
		assertThatThrownBy(() -> DeleteCatalogUseCase.deleteCatalog(catalogId, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class);

		verify(catalogGateway).existsById(catalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo tem categorias associadas")
	void shouldThrowExceptionWhenCatalogHasAssociatedCategories() {
		// Arrange
		Long catalogId = 2L;
		when(catalogGateway.existsById(catalogId)).thenReturn(true);
		when(catalogGateway.existsCategoryByCatalogId(catalogId)).thenReturn(true);

		// Act & Assert
		assertThatThrownBy(() -> DeleteCatalogUseCase.deleteCatalog(catalogId, catalogGateway))
				.isInstanceOf(CatalogConflictException.class);

		verify(catalogGateway).existsById(catalogId);
		verify(catalogGateway).existsCategoryByCatalogId(catalogId);
	}
}
