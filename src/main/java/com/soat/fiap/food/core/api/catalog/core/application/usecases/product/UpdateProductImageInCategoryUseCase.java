package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.ImageStorageGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar imagem do produto.
 */
@Slf4j
public class UpdateProductImageInCategoryUseCase {

	/**
	 * Atualiza apenas a imagem de um produto existente.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria do produto
	 * @param productId
	 *            ID do produto
	 * @param imageFile
	 *            Arquivo da nova imagem
	 * @param catalogGateway
	 *            Gateway de catalogo para comunicação com o mundo exterior
	 * @param imageStorageGateway
	 *            Gateway de imagens para comunicação com o mundo exterior
	 * @return Catalogo o produto atualizada
	 * @throws CatalogNotFoundException
	 *             se o catálogo não for encontrado
	 * @throws IllegalArgumentException
	 *             se o arquivo de imagem for nulo ou vazio
	 * @throws RuntimeException
	 *             se ocorrer um erro durante o upload da imagem
	 */
	public static Catalog updateProductImageInCategory(Long catalogId, Long categoryId, Long productId,
			FileUploadDTO imageFile, CatalogGateway catalogGateway, ImageStorageGateway imageStorageGateway) {
		var catalog = catalogGateway.findById(catalogId);

		if (catalog.isEmpty()) {
			log.warn("Tentativa de excluir produto com catálogo inexistente. Id: {}", catalogId);
			throw new CatalogNotFoundException("Catalogo", catalogId);
		}

		if (imageFile == null) {
			log.warn("Tentativa de upload de imagem com arquivo vazio ou nulo");
			throw new IllegalArgumentException("O arquivo de imagem não pode ser vazio");
		}

		var product = catalog.get().getProductFromCategoryById(categoryId, productId);

		try {
			log.debug("Processando upload de imagem para produto ID: {}", productId);

			if (product.getImageUrl() != null && !product.imageUrlIsEmpty()) {
				String currentImagePath = product.getImageUrlValue();
				imageStorageGateway.deleteImage(currentImagePath);
			}

			String storagePath = "products/" + productId;
			String imagePath = imageStorageGateway.uploadImage(storagePath, imageFile);

			product.setImageUrlValue(imagePath);

			catalog.get().updateProductInCategory(categoryId, product);

			return catalog.get();

		} catch (Exception e) {
			log.error("Erro ao processar upload de imagem: {}", e.getMessage(), e);
			throw new RuntimeException("Falha ao processar imagem: " + e.getMessage(), e);
		}
	}
}
