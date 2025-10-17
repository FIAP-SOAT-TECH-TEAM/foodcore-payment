package com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CatalogInput;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CatalogRequest;

/**
 * Classe utilitária responsável por mapear objetos entre diferentes camadas da
 * aplicação, especialmente entre as camadas de interface (web.api), aplicação
 * (input) e domínio.
 * <p>
 * Atua como um tradutor entre {@link CatalogRequest}, {@link CatalogInput} e
 * {@link Catalog}.
 */
public class CatalogMapper {

	/**
	 * Converte um {@link CatalogRequest} (camada web.api) em um
	 * {@link CatalogInput} (camada de aplicação).
	 *
	 * @param request
	 *            O DTO da requisição HTTP.
	 * @return Um objeto {@link CatalogInput} representando os dados a serem
	 *         processados pela aplicação.
	 */
	public static CatalogInput toInput(CatalogRequest request) {
		return new CatalogInput(request.getName());
	}

	/**
	 * Converte um {@link CatalogInput} (camada de aplicação) em um {@link Catalog}
	 * (entidade de domínio).
	 *
	 * @param input
	 *            O DTO de entrada da aplicação.
	 * @return Uma entidade {@link Catalog} representando o modelo de domínio.
	 */
	public static Catalog toDomain(CatalogInput input) {
		return new Catalog(input.name());
	}
}
