package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.RemoveCategoryFromCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("RemoveCategoryFromCatalogUseCase - Testes Unitários")
class RemoveCategoryFromCatalogUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve remover categoria do catálogo com sucesso")
	void shouldRemoveCategoryFromCatalogSuccessfully() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;
		var catalog = CatalogFixture.createCatalogWithCategories();
		catalog.setId(catalogId);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = RemoveCategoryFromCatalogUseCase.removeCategoryFromCatalog(catalogId, categoryId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalogId);

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não for encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		var catalogId = 999L;
		var categoryId = 1L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(
				() -> RemoveCategoryFromCatalogUseCase.removeCategoryFromCatalog(catalogId, categoryId, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class)
				.hasMessage("Catalogo não encontrado com id: 999");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve manter propriedades do catálogo inalteradas após remover categoria")
	void shouldKeepCatalogPropertiesUnchangedAfterRemovingCategory() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 2L; // Categoria Bebidas
		var catalog = CatalogFixture.createCatalogWithCategories();
		catalog.setId(catalogId);
		var originalName = catalog.getName();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = RemoveCategoryFromCatalogUseCase.removeCategoryFromCatalog(catalogId, categoryId, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalogId);
		assertThat(result.getName()).isEqualTo(originalName);

		verify(catalogGateway).findById(catalogId);
	}
}
