package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api;

import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CategoryResponse;

/**
 * Presenter responsável por converter objetos do domínio {@link Category} em
 * objetos de resposta {@link CategoryResponse} utilizados especificamente na
 * camada de API web (web.api).
 */
public class CategoryPresenter {

	/**
	 * Converte uma instância da entidade {@link Category} para um
	 * {@link CategoryResponse}, utilizado na exposição de dados via API REST
	 * (web.api).
	 *
	 * @param category
	 *            A entidade de domínio {@link Category} a ser convertida.
	 * @return Um DTO {@link CategoryResponse} com os dados da categoria formatados
	 *         para resposta HTTP.
	 */
	public static CategoryResponse toCategoryResponse(Category category) {
		return new CategoryResponse(category.getId(), category.getName(), category.getDescription(),
				category.getImageUrlValue(), category.getDisplayOrder(), category.isActive(), category.getCreatedAt(),
				category.getUpdatedAt());
	}

	/**
	 * Converte uma lista de instâncias da entidade {@link Category} para uma lista
	 * de {@link CategoryResponse}, utilizada na exposição de dados via API REST
	 * (web.api).
	 *
	 * @param categories
	 *            A lista de entidades de domínio {@link Category} a serem
	 *            convertidas.
	 * @return Uma lista de DTOs {@link CategoryResponse} com os dados das
	 *         categorias formatados para resposta HTTP.
	 */
	public static List<CategoryResponse> toListCategoryResponse(List<Category> categories) {
		return categories.stream().map(CategoryPresenter::toCategoryResponse).toList();
	}
}
