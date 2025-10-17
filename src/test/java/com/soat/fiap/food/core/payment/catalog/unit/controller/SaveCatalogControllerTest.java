package com.soat.fiap.food.core.payment.catalog.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.payment.catalog.core.interfaceadapters.bff.controller.web.api.catalog.SaveCatalogController;
import com.soat.fiap.food.core.payment.catalog.core.interfaceadapters.dto.CatalogDTO;
import com.soat.fiap.food.core.payment.catalog.core.interfaceadapters.dto.mappers.CatalogDTOMapper;
import com.soat.fiap.food.core.payment.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.payment.catalog.infrastructure.in.web.api.dto.requests.CatalogRequest;
import com.soat.fiap.food.core.payment.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("SaveCatalogController - Testes Unitários")
class SaveCatalogControllerTest {

	@Mock
	private CatalogDataSource catalogDataSource;

	@Test @DisplayName("Deve salvar catálogo com sucesso")
	void shouldSaveCatalogSuccessfully() {
		// Arrange
		var catalogRequest = CatalogRequest.builder().name("Lanche").build();
		var catalog = CatalogFixture.createValidCatalog(catalogRequest.getName());
		CatalogDTO catalogDTO = CatalogDTOMapper.toDTO(catalog);

		when(catalogDataSource.save(any())).thenReturn(catalogDTO);
		when(catalogDataSource.existsByName(any())).thenReturn(false);

		// Act
		var response = assertDoesNotThrow(() -> SaveCatalogController.saveCatalog(catalogRequest, catalogDataSource));

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.getName()).isEqualTo("Lanche");
		verify(catalogDataSource).save(any());
	}

	@Test @DisplayName("Deve executar fluxo de criação sem exceções")
	void shouldExecuteCreationFlowWithoutExceptions() {
		// Arrange
		var catalogRequest = CatalogRequest.builder().name("Bebidas").build();
		var catalog = CatalogFixture.createValidCatalog();
		CatalogDTO catalogDTO = CatalogDTOMapper.toDTO(catalog);

		when(catalogDataSource.save(any())).thenReturn(catalogDTO);
		when(catalogDataSource.existsByName(any())).thenReturn(false);

		// Act & Assert
		assertDoesNotThrow(() -> SaveCatalogController.saveCatalog(catalogRequest, catalogDataSource));

		verify(catalogDataSource).existsByName("Bebidas");
		verify(catalogDataSource).save(any());
	}

	@Test @DisplayName("Deve retornar resposta válida para catálogo criado")
	void shouldReturnValidResponseForCreatedCatalog() {
		// Arrange
		var catalogRequest = CatalogRequest.builder().name("Sobremesas").build();
		var catalog = CatalogFixture.createValidCatalog(catalogRequest.getName());
		CatalogDTO catalogDTO = CatalogDTOMapper.toDTO(catalog);

		when(catalogDataSource.save(any())).thenReturn(catalogDTO);
		when(catalogDataSource.existsByName(any())).thenReturn(false);

		// Act
		var response = SaveCatalogController.saveCatalog(catalogRequest, catalogDataSource);

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.getName()).isEqualTo("Sobremesas");
		verify(catalogDataSource).save(any());
	}
}
