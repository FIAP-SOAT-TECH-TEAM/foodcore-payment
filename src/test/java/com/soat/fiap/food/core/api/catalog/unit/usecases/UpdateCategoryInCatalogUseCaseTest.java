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

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CategoryInput;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.UpdateCategoryInCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("UpdateCategoryInCatalogUseCase - Testes Unitários")
class UpdateCategoryInCatalogUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve atualizar categoria no mesmo catálogo com sucesso")
	void shouldUpdateCategoryInSameCatalogSuccessfully() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;
		var catalog = CatalogFixture.createCatalogWithCategories();
		catalog.setId(catalogId);

		var categoryInput = new CategoryInput(catalogId, "Categoria Atualizada", "Nova descrição", true, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateCategoryInCatalogUseCase.updateCategoryInCatalog(catalogId, categoryId, categoryInput,
				catalogGateway);

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
		var categoryInput = new CategoryInput(catalogId, "Categoria Teste", "Descrição teste", true, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> UpdateCategoryInCatalogUseCase.updateCategoryInCatalog(catalogId, categoryId,
				categoryInput, catalogGateway)).isInstanceOf(CatalogNotFoundException.class)
				.hasMessage("Catalogo não encontrado com id: 999");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve mover categoria para novo catálogo com sucesso")
	void shouldMoveCategoryToNewCatalogSuccessfully() {
		// Arrange
		var originalCatalogId = 1L;
		var newCatalogId = 2L;
		var categoryId = 1L;

		var originalCatalog = CatalogFixture.createCatalogWithCategories();
		originalCatalog.setId(originalCatalogId);

		var newCatalog = CatalogFixture.createEmptyCatalog();
		newCatalog.setId(newCatalogId);
		newCatalog.setName("Novo Catálogo");

		var categoryInput = new CategoryInput(newCatalogId, "Categoria Movida", "Categoria movida para novo catálogo",
				true, 1);

		when(catalogGateway.findById(originalCatalogId)).thenReturn(Optional.of(originalCatalog));
		when(catalogGateway.findById(newCatalogId)).thenReturn(Optional.of(newCatalog));

		// Act
		var result = UpdateCategoryInCatalogUseCase.updateCategoryInCatalog(originalCatalogId, categoryId,
				categoryInput, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(newCatalogId);
		assertThat(result.getName()).isEqualTo("Novo Catálogo");

		verify(catalogGateway).findById(originalCatalogId);
		verify(catalogGateway).findById(newCatalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando novo catálogo não for encontrado")
	void shouldThrowExceptionWhenNewCatalogNotFound() {
		// Arrange
		var originalCatalogId = 1L;
		var newCatalogId = 999L;
		var categoryId = 1L;

		var originalCatalog = CatalogFixture.createCatalogWithCategories();
		originalCatalog.setId(originalCatalogId);

		var categoryInput = new CategoryInput(newCatalogId, "Categoria Teste", "Descrição teste", true, 1);

		when(catalogGateway.findById(originalCatalogId)).thenReturn(Optional.of(originalCatalog));
		when(catalogGateway.findById(newCatalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> UpdateCategoryInCatalogUseCase.updateCategoryInCatalog(originalCatalogId, categoryId,
				categoryInput, catalogGateway)).isInstanceOf(CatalogNotFoundException.class)
				.hasMessage("Catalogo não encontrado com id: 999");

		verify(catalogGateway).findById(originalCatalogId);
		verify(catalogGateway).findById(newCatalogId);
	}

	@Test @DisplayName("Deve manter propriedades do catálogo inalteradas após atualizar categoria")
	void shouldKeepCatalogPropertiesUnchangedAfterUpdatingCategory() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;
		var catalog = CatalogFixture.createCatalogWithCategories();
		catalog.setId(catalogId);
		var originalName = catalog.getName();

		var categoryInput = new CategoryInput(catalogId, "Categoria Atualizada", "Descrição atualizada", false, 10);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateCategoryInCatalogUseCase.updateCategoryInCatalog(catalogId, categoryId, categoryInput,
				catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalogId);
		assertThat(result.getName()).isEqualTo(originalName);

		verify(catalogGateway).findById(catalogId);
	}
}
