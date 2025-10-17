package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api;

import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.ProductResponse;

/**
 * Presenter responsável por converter objetos do domínio {@link Product} em
 * objetos de resposta {@link ProductResponse} utilizados na camada de API web
 * (web.api).
 */
public class ProductPresenter {

	/**
	 * Converte uma instância da entidade {@link Product} para um
	 * {@link ProductResponse}.
	 *
	 * @param product
	 *            A entidade de domínio {@link Product} a ser convertida.
	 * @return Um DTO {@link ProductResponse} com os dados do produto formatados
	 *         para resposta HTTP.
	 */
	public static ProductResponse toProductResponse(Product product) {
		Category category = product.getCategory();

		return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
				product.getImageUrlValue(), product.isActive(), category != null && category.isActive(),
				product.getDisplayOrder(), StockPresenter.toStockResponse(product.getStock()), product.getCreatedAt(),
				product.getUpdatedAt());
	}

	/**
	 * Converte uma lista de instâncias da entidade {@link Product} para uma lista
	 * de {@link ProductResponse}.
	 *
	 * @param products
	 *            A lista de entidades de domínio {@link Product} a serem
	 *            convertidas.
	 * @return Uma lista de DTOs {@link ProductResponse} com os dados dos produtos
	 *         formatados para resposta HTTP.
	 */
	public static List<ProductResponse> toListProductResponse(List<Product> products) {
		return products.stream().map(ProductPresenter::toProductResponse).toList();
	}
}
