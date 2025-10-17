package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.presenter.web.api;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.StockResponse;

/**
 * Presenter responsável por converter objetos do domínio {@link Stock} em
 * objetos de resposta {@link StockResponse} utilizados na camada de API web
 * (web.api).
 */
public class StockPresenter {

	/**
	 * Converte uma instância da entidade {@link Stock} para um
	 * {@link StockResponse}.
	 *
	 * @param stock
	 *            A entidade de domínio {@link Stock} a ser convertida.
	 * @return Um DTO {@link StockResponse} com os dados do estoque formatados para
	 *         resposta HTTP.
	 */
	public static StockResponse toStockResponse(Stock stock) {
		return new StockResponse(stock.getId(), stock.getQuantity(), stock.getCreatedAt(), stock.getUpdatedAt());
	}
}
