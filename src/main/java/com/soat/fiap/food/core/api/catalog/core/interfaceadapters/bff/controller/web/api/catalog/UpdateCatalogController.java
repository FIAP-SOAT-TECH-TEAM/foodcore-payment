package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.catalog;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CatalogMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.UpdateCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api.CatalogPresenter;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CatalogResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar catálogo.
 */
@Slf4j
public class UpdateCatalogController {

	/**
	 * Atualiza um catálogo.
	 *
	 * @param catalogRequest
	 *            Catálogo a ser atualizado
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @return Catálogo atualizado com identificadores atualizados
	 */
	public static CatalogResponse updateCatalog(Long id, CatalogRequest catalogRequest,
			CatalogDataSource catalogDataSource) {

		var gateway = new CatalogGateway(catalogDataSource);

		var catalogInput = CatalogMapper.toInput(catalogRequest);

		var catalog = UpdateCatalogUseCase.updateCatalog(id, catalogInput, gateway);

		var updatedCatalog = gateway.save(catalog);

		log.debug("Catalogo atualizado com sucesso: {}", id);

		return CatalogPresenter.toCatalogResponse(updatedCatalog);
	}
}
