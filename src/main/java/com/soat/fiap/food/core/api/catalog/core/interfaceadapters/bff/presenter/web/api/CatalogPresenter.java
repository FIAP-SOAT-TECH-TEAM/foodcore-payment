package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api;

import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CatalogResponse;

/**
 * Presenter responsável por converter objetos do domínio {@link Catalog} em
 * objetos de resposta {@link CatalogResponse} utilizados especificamente na
 * camada de API web (web.api).
 */
public class CatalogPresenter {

	/**
	 * Converte uma instância da entidade {@link Catalog} para um
	 * {@link CatalogResponse}, utilizado na exposição de dados via API REST
	 * (web.api).
	 *
	 * @param catalog
	 *            A entidade de domínio {@link Catalog} a ser convertida.
	 * @return Um DTO {@link CatalogResponse} com os dados do catálogo formatados
	 *         para resposta HTTP.
	 */
	public static CatalogResponse toCatalogResponse(Catalog catalog) {
		return new CatalogResponse(catalog.getId(), catalog.getName(), catalog.getCreatedAt(), catalog.getUpdatedAt());
	}

	/**
	 * Converte uma lista de instâncias da entidade {@link Catalog} para uma lista
	 * de {@link CatalogResponse}, utilizada na exposição de dados via API REST
	 * (web.api).
	 *
	 * @param catalogs
	 *            A lista de entidades de domínio {@link Catalog} a serem
	 *            convertidas.
	 * @return Uma lista de DTOs {@link CatalogResponse} com os dados dos catálogos
	 *         formatados para resposta HTTP.
	 */
	public static List<CatalogResponse> toListCatalogResponse(List<Catalog> catalogs) {
		return catalogs.stream()
				.map(catalog -> new CatalogResponse(catalog.getId(), catalog.getName(), catalog.getCreatedAt(),
						catalog.getUpdatedAt()))
				.toList();
	}
}
