package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductInput;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.UpdateProductInCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("UpdateProductInCategoryUseCase - Testes Unitários")
class UpdateProductInCategoryUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve atualizar produto na mesma categoria com sucesso")
	void shouldUpdateProductInSameCategorySuccessfully() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();
		catalog.setId(catalogId);

		var productInput = new ProductInput(categoryId, "Big Mac Atualizado",
				"Hambúrguer atualizado com novos ingredientes", new BigDecimal("29.90"), 15, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductInCategoryUseCase.updateProductInCategory(catalogId, categoryId, productId,
				productInput, catalogGateway);

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
		var productId = 1L;
		var productInput = new ProductInput(categoryId, "Produto Teste", "Descrição teste", new BigDecimal("10.00"), 10,
				1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> UpdateProductInCategoryUseCase.updateProductInCategory(catalogId, categoryId,
				productId, productInput, catalogGateway)).isInstanceOf(CatalogNotFoundException.class)
				.hasMessage("Catalogo não encontrado com id: 999");

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve mover produto para nova categoria quando categoryId for diferente")
	void shouldMoveProductToNewCategoryWhenCategoryIdIsDifferent() {
		// Arrange
		var catalogId = 1L;
		var originalCategoryId = 1L;
		var newCategoryId = 2L;
		var productId = 1L;
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();
		catalog.setId(catalogId);

		var productInput = new ProductInput(newCategoryId, // Categoria diferente
				"Big Mac Movido", "Hambúrguer movido para categoria de bebidas", new BigDecimal("25.90"), 10, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductInCategoryUseCase.updateProductInCategory(catalogId, originalCategoryId, productId,
				productInput, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalogId);

		verify(catalogGateway).findById(catalogId);
	}

	@Test @DisplayName("Deve manter propriedades do catálogo inalteradas ao atualizar produto")
	void shouldKeepCatalogPropertiesUnchangedWhenUpdatingProduct() {
		// Arrange
		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;
		var catalog = CatalogFixture.createCatalogWithMultipleProducts();
		catalog.setId(catalogId);
		var originalName = catalog.getName();
		var originalCategoriesCount = catalog.getCategories().size();

		var productInput = new ProductInput(categoryId, "Produto Atualizado", "Descrição atualizada",
				new BigDecimal("20.00"), 20, 1);

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act
		var result = UpdateProductInCategoryUseCase.updateProductInCategory(catalogId, categoryId, productId,
				productInput, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(originalName);
		assertThat(result.getCategories()).hasSize(originalCategoriesCount);
		assertThat(result.getId()).isEqualTo(catalogId);

		verify(catalogGateway).findById(catalogId);
	}
}
