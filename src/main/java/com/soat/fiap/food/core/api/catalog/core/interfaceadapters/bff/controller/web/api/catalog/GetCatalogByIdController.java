package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.catalog;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.GetCatalogByIdUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.CatalogPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CatalogResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Buscar um catálogo pelo seu ID.
 */
@Slf4j
public class GetCatalogByIdController {

	/**
	 * Busca um catálogo pelo seu ID.
	 *
	 * @param id
	 *            Identificador do catálogo
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @return o catálogo
	 */
	public static CatalogResponse getCatalogById(Long id, CatalogDataSource catalogDataSource) {
		log.debug("Buscando catalogo de id: {}", id);

		var gateway = new CatalogGateway(catalogDataSource);

		var existingCatalog = GetCatalogByIdUseCase.getCatalogById(id, gateway);

		return CatalogPresenter.toCatalogResponse(existingCatalog);
	}
}
