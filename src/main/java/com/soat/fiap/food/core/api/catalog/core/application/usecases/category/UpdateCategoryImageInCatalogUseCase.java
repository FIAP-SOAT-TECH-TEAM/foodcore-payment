package com.soat.fiap.food.core.api.catalog.core.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.ImageStorageGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar imagem da categoria.
 */
@Slf4j
public class UpdateCategoryImageInCatalogUseCase {

	/**
	 * Atualiza apenas a imagem de uma categoria existente.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria do categoria
	 * @param imageFile
	 *            Arquivo da nova imagem
	 * @param catalogGateway
	 *            Gateway de catalogo para comunicação com o mundo exterior
	 * @param imageStorageGateway
	 *            Gateway de imagens para comunicação com o mundo exterior
	 * @return Catalogo com a categoria atualizada
	 * @throws CatalogNotFoundException
	 *             se o catálogo não for encontrado
	 * @throws IllegalArgumentException
	 *             se o arquivo de imagem for nulo ou vazio
	 * @throws RuntimeException
	 *             se ocorrer um erro durante o upload da imagem
	 */
	public static Catalog updateCategoryImageInCatalog(Long catalogId, Long categoryId, FileUploadDTO imageFile,
			CatalogGateway catalogGateway, ImageStorageGateway imageStorageGateway) {
		var catalog = catalogGateway.findById(catalogId);

		if (catalog.isEmpty()) {
			log.warn("Tentativa de atualizar imagem de categoria com catálogo inexistente. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		}

		if (imageFile == null) {
			log.warn("Tentativa de upload de imagem com arquivo vazio ou nulo");
			throw new IllegalArgumentException("O arquivo de imagem não pode ser vazio");
		}

		var category = catalog.get().getCategoryById(categoryId);

		try {
			log.debug("Processando upload de imagem para categoria ID: {}", categoryId);

			if (category.getImageUrl() != null && !category.imageUrlIsEmpty()) {
				var currentImagePath = category.getImageUrlValue();
				imageStorageGateway.deleteImage(currentImagePath);
			}

			var storagePath = "categories/" + categoryId;
			var imagePath = imageStorageGateway.uploadImage(storagePath, imageFile);

			category.setImageUrlValue(imagePath);

			catalog.get().updateCategory(category);

			return catalog.get();

		} catch (Exception e) {
			log.error("Erro ao processar upload de imagem: {}", e.getMessage(), e);
			throw new RuntimeException("Falha ao processar imagem: " + e.getMessage(), e);
		}
	}
}
