package com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Remover catálogo pelo seu identificador.
 */
@Slf4j
public class DeleteCatalogUseCase {

	/**
	 * Remove um catálogo pelo seu ID.
	 *
	 * @param id
	 *            Identificador do catálogo a ser removido
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 */
	public static void deleteCatalog(Long id, CatalogGateway gateway) {
		if (!gateway.existsById(id)) {
			log.warn("Tentativa de excluir catalogo inexistente. Id: {}", id);
			throw new CatalogNotFoundException("Catalogo", id);
		}
		if (gateway.existsCategoryByCatalogId(id)) {
			log.warn("Tentativa de excluir catalogo com categorias associadas. Id: {}", id);
			throw new CatalogConflictException(
					"Não é possível excluir este catalogo porque existem categorias associadas a ele");
		}

		gateway.delete(id);

		log.debug("Catalogo excluido com sucesso: {}", id);
	}
}
