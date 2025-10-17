package com.soat.fiap.food.core.api.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.UpdateCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("UpdateCatalogUseCase - Testes Unitários")
class UpdateCatalogUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve atualizar catálogo com sucesso quando dados válidos são fornecidos")
	void shouldUpdateCatalogSuccessfullyWhenValidDataProvided() {
		// Arrange
		var catalogId = 1L;
		var existingCatalog = CatalogFixture.createValidCatalog();
		existingCatalog.setId(catalogId);
		var catalogInput = CatalogFixture.createValidCatalogInput();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(existingCatalog));
		when(catalogGateway.existsByNameAndIdNot(catalogInput.name(), catalogId)).thenReturn(false);

		// Act
		var result = UpdateCatalogUseCase.updateCatalog(catalogId, catalogInput, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalogId);
		assertThat(result.getName()).isEqualTo(catalogInput.name());
		assertThat(result.getUpdatedAt()).isNotNull();
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não for encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		var catalogId = 999L;
		var catalogInput = CatalogFixture.createValidCatalogInput();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> UpdateCatalogUseCase.updateCatalog(catalogId, catalogInput, catalogGateway))
				.isInstanceOf(CatalogNotFoundException.class)
				.hasMessage("Catalogo não encontrado com id: 999");
	}

	@Test @DisplayName("Deve lançar exceção quando nome já existe para outro catálogo")
	void shouldThrowExceptionWhenNameAlreadyExistsForAnotherCatalog() {
		// Arrange
		var catalogId = 1L;
		var existingCatalog = CatalogFixture.createValidCatalog();
		existingCatalog.setId(catalogId);
		var catalogInput = CatalogFixture.createConflictingCatalogInput();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(existingCatalog));
		when(catalogGateway.existsByNameAndIdNot(catalogInput.name(), catalogId)).thenReturn(true);

		// Act & Assert
		assertThatThrownBy(() -> UpdateCatalogUseCase.updateCatalog(catalogId, catalogInput, catalogGateway))
				.isInstanceOf(CatalogConflictException.class)
				.hasMessage("Já existe um(a) Catalogo com Nome: Catálogo Principal");
	}

	@Test @DisplayName("Deve permitir atualizar catálogo mantendo o mesmo nome")
	void shouldAllowUpdateCatalogKeepingSameName() {
		// Arrange
		var catalogId = 1L;
		var existingCatalog = CatalogFixture.createValidCatalog();
		existingCatalog.setId(catalogId);
		var catalogInput = CatalogFixture.createConflictingCatalogInput();

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(existingCatalog));
		when(catalogGateway.existsByNameAndIdNot(catalogInput.name(), catalogId)).thenReturn(false);

		// Act
		var result = UpdateCatalogUseCase.updateCatalog(catalogId, catalogInput, catalogGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(catalogId);
		assertThat(result.getName()).isEqualTo(catalogInput.name());
		assertThat(result.getUpdatedAt()).isNotNull();
	}
}
