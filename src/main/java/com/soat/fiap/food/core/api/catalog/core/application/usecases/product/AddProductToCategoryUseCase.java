package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.ProductMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Adicionar um produto em uma categoria de um catálogo.
 */
@Slf4j
public class AddProductToCategoryUseCase {

	/**
	 * Adiciona um produto a uma categoria de um catálogo.
	 *
	 * @param catalogId
	 *            ID do catalogo
	 * @param productInput
	 *            Dados do produto
	 * @param gateway
	 *            Gateway do catálogo
	 * @return Catálogo com o produto adicionado a categoria
	 */
	public static Catalog addProductToCategory(Long catalogId, ProductInput productInput, CatalogGateway gateway) {
		log.debug("Criando produto: {}", productInput.name());

		var product = ProductMapper.toDomain(productInput);
		var catalog = gateway.findById(catalogId);

		if (catalog.isEmpty()) {
			log.warn("Tentativa de cadastrar produto em categoria inexistente. ID: {}", productInput.categoryId());
			throw new CatalogNotFoundException("Catalogo", productInput.categoryId());
		}

		catalog.get().addProductToCategory(productInput.categoryId(), product);

		return catalog.get();
	}
}
