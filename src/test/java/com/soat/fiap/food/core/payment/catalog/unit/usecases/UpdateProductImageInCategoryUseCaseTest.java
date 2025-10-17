package com.soat.fiap.food.core.payment.catalog.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.soat.fiap.food.core.payment.catalog.core.application.usecases.product.UpdateProductImageInCategoryUseCase;
import com.soat.fiap.food.core.payment.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.payment.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.payment.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.payment.shared.core.interfaceadapters.gateways.ImageStorageGateway;
import com.soat.fiap.food.core.payment.shared.fixtures.CatalogFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("UpdateProductImageInCategoryUseCase - Testes Unitários")
class UpdateProductImageInCategoryUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Mock
	private ImageStorageGateway imageStorageGateway;

	@Mock
	private MultipartFile imageFile;

	@Test @DisplayName("Deve atualizar imagem do produto com sucesso")
	void shouldUpdateProductImageSuccessfully() throws IOException {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;
		var newImagePath = "products/1/new-image.jpg";

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		var fileUploadDTO = new FileUploadDTO("new-image.jpg", new byte[]{1, 2, 3});
		var imagePath = "products/" + productId;
		when(imageStorageGateway.uploadImage(anyString(), eq(fileUploadDTO))).thenReturn(newImagePath);

		// Act
		var result = UpdateProductImageInCategoryUseCase.updateProductImageInCategory(catalogId, categoryId, productId,
				fileUploadDTO, catalogGateway, imageStorageGateway);

		// Assert
		assertThat(result).isNotNull();
		verify(catalogGateway).findById(catalogId);
		verify(imageStorageGateway).uploadImage(eq(imagePath), eq(fileUploadDTO));
	}

	@Test @DisplayName("Deve excluir imagem anterior quando produto já possui imagem")
	void shouldDeletePreviousImageWhenProductAlreadyHasImage() throws IOException {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var product = catalog.getProductFromCategoryById(1L, 1L);
		product.setImageUrlValue("products/1/old-image.jpg");

		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;
		var newImagePath = "products/1/new-image.jpg";

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		var fileUploadDTO = new FileUploadDTO("new-image.jpg", new byte[]{1, 2, 3});
		var imagePath = "products/" + productId;
		when(imageStorageGateway.uploadImage(anyString(), eq(fileUploadDTO))).thenReturn(newImagePath);

		// Act
		var result = UpdateProductImageInCategoryUseCase.updateProductImageInCategory(catalogId, categoryId, productId,
				fileUploadDTO, catalogGateway, imageStorageGateway);

		// Assert
		assertThat(result).isNotNull();
		verify(imageStorageGateway).deleteImage("products/1/old-image.jpg");
		verify(imageStorageGateway).uploadImage(eq(imagePath), eq(fileUploadDTO));
	}

	@Test @DisplayName("Deve lançar exceção quando catálogo não for encontrado")
	void shouldThrowExceptionWhenCatalogNotFound() {
		// Arrange
		var catalogId = 999L;
		var categoryId = 1L;
		var productId = 1L;
		var fileUploadDTO = new FileUploadDTO("new-image.jpg", new byte[]{1, 2, 3});

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> UpdateProductImageInCategoryUseCase.updateProductImageInCategory(catalogId, categoryId,
				productId, fileUploadDTO, catalogGateway, imageStorageGateway))
				.isInstanceOf(CatalogNotFoundException.class);

		verify(catalogGateway).findById(catalogId);
		verifyNoInteractions(imageStorageGateway);
	}

	@Test @DisplayName("Deve lançar exceção quando arquivo de imagem for nulo")
	void shouldThrowExceptionWhenImageFileIsNull() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		// Act & Assert
		assertThatThrownBy(() -> UpdateProductImageInCategoryUseCase.updateProductImageInCategory(catalogId, categoryId,
				productId, null, catalogGateway, imageStorageGateway)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("O arquivo de imagem não pode ser vazio");

		verify(catalogGateway).findById(catalogId);
		verifyNoInteractions(imageStorageGateway);
	}

	@Test @DisplayName("Deve lançar exceção quando arquivo de imagem for vazio")
	void shouldThrowExceptionWhenImageFileIsEmpty() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		FileUploadDTO fileUploadDTO = null;

		// Act & Assert
		assertThatThrownBy(() -> UpdateProductImageInCategoryUseCase.updateProductImageInCategory(catalogId, categoryId,
				productId, fileUploadDTO, catalogGateway, imageStorageGateway))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("O arquivo de imagem não pode ser vazio");

		verify(catalogGateway).findById(catalogId);
		verifyNoInteractions(imageStorageGateway);
	}

	@Test @DisplayName("Deve lançar RuntimeException quando ocorrer erro no upload")
	void shouldThrowRuntimeExceptionWhenUploadErrorOccurs() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var catalogId = 1L;
		var categoryId = 1L;
		var productId = 1L;

		when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

		var fileUploadDTO = new FileUploadDTO("new-image.jpg", new byte[]{1, 2, 3});

		var imagePath = "products/" + productId;

		when(imageStorageGateway.uploadImage(anyString(), eq(fileUploadDTO)))
				.thenThrow(new RuntimeException("Erro no upload"));

		// Act & Assert
		assertThatThrownBy(() -> UpdateProductImageInCategoryUseCase.updateProductImageInCategory(catalogId, categoryId,
				productId, fileUploadDTO, catalogGateway, imageStorageGateway)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Falha ao processar imagem");

		verify(catalogGateway).findById(catalogId);
		verify(imageStorageGateway).uploadImage(eq(imagePath), eq(fileUploadDTO));
	}
}
