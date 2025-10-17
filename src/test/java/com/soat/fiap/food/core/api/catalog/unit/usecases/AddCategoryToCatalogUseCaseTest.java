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
import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.AddCategoryToCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("AddCategoryToCatalogUseCase - Testes Unitários")
class AddCategoryToCatalogUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve adicionar categoria ao catálogo com sucesso")
	void shouldAddCategoryToCatalogSuccessfully() {
		// Arrange
		var catalogId = 1L;
		var catalog = CatalogFixture.createCatalogWithCategories();
		catalog.setId(catalogId);

		var categoryInput = new CategoryInput(catalogId, "Nova Categoria", "Descrição da nova categoria", true, 3);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = AddCategoryToCatalogUseCase.addCategoryToCatalog(categoryInput, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalogId);
		assertThat(result.getCategories()).hasSize(3); // 2 existentes + 1 nova

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não for encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		var catalogId = 999L;
		var categoryInput = new CategoryInput(catalogId, "Nova Categoria", "Descrição da nova categoria", true, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> AddCategoryToCatalogUseCase.addCategoryToCatalog(categoryInput, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class)
				.hasMessage("Catalogo não encontrado com id: 999");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve adicionar categoria com dados mínimos válidos")
	void shouldAddCategoryWithMinimalValidData() {
		// Arrange
		var catalogId = 1L;
		var catalog = CatalogFixture.createCatalogWithCategories();
		catalog.setId(catalogId);

		var categoryInput = new CategoryInput(catalogId, "Categoria Simples", "Descrição simples", true, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = AddCategoryToCatalogUseCase.addCategoryToCatalog(categoryInput, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalogId);
		assertThat(result.getName()).isEqualTo("Catálogo com Categorias");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve manter propriedades do catálogo inalteradas após adicionar categoria")
	void shouldKeepCatalogPropertiesUnchangedAfterAddingCategory() {
		// Arrange
		var catalogId = 1L;
		var catalog = CatalogFixture.createCatalogWithCategories();
		catalog.setId(catalogId);
		var originalName = catalog.getName();

		var categoryInput = new CategoryInput(catalogId, "Categoria Teste", "Descrição teste", true, 5);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = AddCategoryToCatalogUseCase.addCategoryToCatalog(categoryInput, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalogId);
		assertThat(result.getName()).isEqualTo(originalName);

		verify(catalogGateway).findById(catalogId);
	}
}
