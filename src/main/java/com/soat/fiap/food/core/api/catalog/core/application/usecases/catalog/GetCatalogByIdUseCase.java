package com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter catálogo por identificador.
 */
@Slf4j
public class GetCatalogByIdUseCase {

	/**
	 * Busca um catálogo pelo seu ID.
	 *
	 * @param id
	 *            Identificador do catálogo
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return o catálogo
	 */
	public static Catalog getCatalogById(Long id, CatalogGateway gateway) {
		var existingCatalog = gateway.findById(id);

		if (existingCatalog.isEmpty()) {
			log.warn("Catalogo não encontrado. Id: {}", id);
			throw new CatalogNotFoundException("Catalogo", id);
		}

		return existingCatalog.get();
	}
}
