package com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CategoryInput;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CategoryRequest;

/**
 * Classe utilitária responsável por mapear objetos entre diferentes camadas da
 * aplicação, especialmente entre as camadas de interface (web.api), aplicação
 * (input) e domínio.
 * <p>
 * Atua como um tradutor entre {@link CategoryRequest}, {@link CategoryInput} e
 * {@link Category}.
 */
public class CategoryMapper {

	/**
	 * Converte um {@link CategoryRequest} (camada web.api) em um
	 * {@link CategoryInput} (camada de aplicação).
	 *
	 * @param request
	 *            O DTO da requisição HTTP.
	 * @return Um objeto {@link CategoryInput} representando os dados a serem
	 *         processados pela aplicação.
	 */
	public static CategoryInput toInput(CategoryRequest request) {
		return new CategoryInput(request.getCatalogId(), request.getName(), request.getDescription(),
				request.isActive(), request.getDisplayOrder());
	}

	/**
	 * Converte um {@link CategoryInput} (camada de aplicação) em uma entidade
	 * {@link Category} (modelo de domínio).
	 *
	 * @param input
	 *            O DTO de entrada da aplicação.
	 * @return Uma entidade {@link Category} representando o modelo de domínio.
	 */
	public static Category toDomain(CategoryInput input) {
		return new Category(new Details(input.name(), input.description()), new ImageUrl(""), input.displayOrder(),
				input.active());
	}
}
