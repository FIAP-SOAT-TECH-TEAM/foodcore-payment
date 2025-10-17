package com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductInput;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.ProductRequest;

/**
 * Classe utilitária responsável por mapear objetos entre diferentes camadas da
 * aplicação, especialmente entre a camada de aplicação (input) e o domínio.
 * <p>
 * Atua como um tradutor entre {@link ProductInput} e {@link Product}.
 */
public class ProductMapper {
	/**
	 * Converte um {@link ProductRequest} (camada web.api) em um
	 * {@link ProductInput} (camada de aplicação).
	 *
	 * @param request
	 *            O DTO da requisição HTTP.
	 * @return Um objeto {@link ProductInput} representando os dados a serem
	 *         processados pela aplicação.
	 */
	public static ProductInput toInput(ProductRequest request) {
		return new ProductInput(request.getCategoryId(), request.getName(), request.getDescription(),
				request.getPrice(), request.getStockQuantity(), request.getDisplayOrder());
	}

	/**
	 * Converte um {@link ProductInput} (camada de aplicação) em um {@link Product}
	 * (entidade de domínio).
	 *
	 * @param input
	 *            O DTO de entrada da aplicação.
	 * @return Um objeto {@link Product} representando o modelo de domínio.
	 */
	public static Product toDomain(ProductInput input) {
		Stock stock = new Stock(input.stockQuantity());
		Product product = new Product(new Details(input.name(), input.description()), input.price(), new ImageUrl(""),
				input.displayOrder());
		product.setStock(stock);
		return product;
	}
}
