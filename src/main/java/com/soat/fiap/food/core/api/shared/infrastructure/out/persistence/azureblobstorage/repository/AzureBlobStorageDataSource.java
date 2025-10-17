package com.soat.fiap.food.core.api.shared.infrastructure.out.persistence.azureblobstorage.repository;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.ImageDataSource;
import com.soat.fiap.food.core.api.shared.infrastructure.out.persistence.azureblobstorage.config.AzureStorageProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do {@link ImageDataSource} que realiza o upload e a exclusão de
 * imagens no serviço Azure Blob Storage.
 */
@Component @RequiredArgsConstructor @Slf4j
public class AzureBlobStorageDataSource implements ImageDataSource {

	private final AzureStorageProperties properties;

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
	@Override
	public String uploadImage(String path, FileUploadDTO file) {
		log.info("Iniciando upload da imagem para o Azure Blob Storage...");

		try {
			BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
					.connectionString(properties.getConnectionString())
					.buildClient();

			BlobContainerClient containerClient = blobServiceClient
					.getBlobContainerClient(properties.getContainerName());

			if (!containerClient.exists()) {
				log.info("Container não encontrado. Criando container: {}", properties.getContainerName());
				containerClient.create();
			}

			String fileName = UUID.randomUUID() + "-" + file.fileName();
			BlobClient blobClient = containerClient.getBlobClient(path + "/" + fileName);

			blobClient.upload(new ByteArrayInputStream(file.content()), file.content().length, true);

			log.info("Upload concluído com sucesso. URL: {}", blobClient.getBlobUrl());
			return blobClient.getBlobUrl();

		} catch (Exception e) {
			log.error("Erro ao fazer upload da imagem para o Azure Blob", e);
			throw new RuntimeException("Erro ao fazer upload da imagem para o Azure Blob", e);
		}
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
	@Override
	public void deleteImage(String imageUrl) {
		log.info("Iniciando exclusão da imagem no Azure Blob Storage...");

		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
				.connectionString(properties.getConnectionString())
				.buildClient();

		BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(properties.getContainerName());

		if (!containerClient.exists()) {
			log.error("Container Azure Blob não existe: {}", properties.getContainerName());
			throw new RuntimeException("Container Azure Blob não existe: " + properties.getContainerName());
		}

		String containerUrl = String.format("https://%s.blob.core.windows.net/%s/", blobServiceClient.getAccountName(),
				properties.getContainerName());

		if (!imageUrl.startsWith(containerUrl)) {
			log.error("URL não pertence ao container configurado: {}", imageUrl);
			throw new IllegalArgumentException("URL não pertence ao container configurado.");
		}

		String blobPath = imageUrl.substring(containerUrl.length());
		BlobClient blobClient = containerClient.getBlobClient(blobPath);

		if (blobClient.exists()) {
			blobClient.delete();
			log.info("Imagem excluída com sucesso: {}", imageUrl);
		} else {
			log.warn("Blob não encontrado para exclusão: {}", blobPath);
			throw new RuntimeException("Blob não encontrado: " + blobPath);
		}
	}
}
