package com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.ImageDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Gateway para persistência de imagens.
 */
@Slf4j
public class ImageStorageGateway {

	private final ImageDataSource imageDataSource;

	public ImageStorageGateway(ImageDataSource imageDataSource) {
		this.imageDataSource = imageDataSource;
	}

	/**
	 * Realiza o upload de uma imagem para o Azure Blob Storage, em um caminho
	 * específico dentro do container.
	 *
	 * @param path
	 *            o caminho (dentro do container) onde a imagem será armazenada, por
	 *            exemplo: "catalogs/1/categories/5"
	 * @param file
	 *            o arquivo de imagem a ser enviado
	 * @return a URL pública da imagem armazenada
	 * @throws RuntimeException
	 *             se ocorrer uma falha ao realizar o upload
	 */
	public String uploadImage(String path, FileUploadDTO file) {
		var url = imageDataSource.uploadImage(path, file);
		log.debug("Nova imagem enviada para o caminho: {}", path);
		return url;
	}

	/**
	 * Exclui uma imagem do Azure Blob Storage com base na URL pública do blob.
	 *
	 * @param imageUrl
	 *            a URL completa da imagem no Azure Blob Storage
	 * @throws IllegalArgumentException
	 *             se a URL não pertencer ao container configurado
	 * @throws RuntimeException
	 *             se o container não existir ou se o blob não for encontrado
	 */
	public void deleteImage(String imageUrl) {
		log.debug("Removendo imagem: {}", imageUrl);
		imageDataSource.deleteImage(imageUrl);
	}
}
