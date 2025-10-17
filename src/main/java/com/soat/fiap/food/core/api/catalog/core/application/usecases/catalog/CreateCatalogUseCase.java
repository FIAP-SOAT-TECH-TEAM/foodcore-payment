package com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CatalogInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CatalogMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Criar catálogo.
 */
@Slf4j
public class CreateCatalogUseCase {

	/**
	 * Cria um catálogo.
	 *
	 * @param catalogInput
	 *            Catálogo a ser criado
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Catálogo criado
	 */
	public static Catalog createCatalog(CatalogInput catalogInput, CatalogGateway gateway) {

		var catalog = CatalogMapper.toDomain(catalogInput);

		log.debug("Criando catalogo: {}", catalog.getName());

		if (gateway.existsByName(catalog.getName())) {
			log.warn("Tentativa de cadastrar catalogo com nome repetido. Nome: {}", catalog.getName());
			throw new CatalogConflictException("Catalogo", "Nome", catalog.getName());
		}

		log.debug("Catalogo criado: {}", catalog.getName());

		return catalog;
	}
}
