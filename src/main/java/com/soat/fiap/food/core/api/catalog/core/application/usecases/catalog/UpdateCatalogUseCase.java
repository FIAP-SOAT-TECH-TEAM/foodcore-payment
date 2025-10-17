package com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CatalogInput;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar catálogo.
 */
@Slf4j
public class UpdateCatalogUseCase {

	/**
	 * Atualiza um catálogo.
	 *
	 * @param id
	 *            Identificador do catálogo
	 * @param catalogInput
	 *            Catálogo a ser atualizado
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return Catálogo atualizado
	 */
	public static Catalog updateCatalog(Long id, CatalogInput catalogInput, CatalogGateway gateway) {

		var existingCatalog = gateway.findById(id);

		log.debug("Atualizando catalogo: {}", id);

		if (existingCatalog.isEmpty()) {
			log.warn("Tentativa de atualizar catalogo inexistente. Id: {}", id);
			throw new CatalogNotFoundException("Catalogo", id);
		} else if (gateway.existsByNameAndIdNot(catalogInput.name(), id)) {
			log.warn("Tentativa de cadastrar catalogo com nome repetido. Nome: {}", catalogInput.name());
			throw new CatalogConflictException("Catalogo", "Nome", catalogInput.name());
		}

		existingCatalog.get().setName(catalogInput.name());

		existingCatalog.get().markUpdatedNow();

		return existingCatalog.get();
	}
}
