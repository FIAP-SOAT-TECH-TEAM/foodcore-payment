package com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.StockInput;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;

/**
 * Classe utilitária responsável por mapear objetos entre diferentes camadas da
 * aplicação, especialmente entre a camada de aplicação (input) e o domínio.
 * <p>
 * Atua como um tradutor entre {@link StockInput} e {@link Stock}.
 */
public class StockMapper {

	/**
	 * Converte um {@link StockInput} (camada de aplicação) em um {@link Stock}
	 * (entidade de domínio).
	 *
	 * @param input
	 *            O DTO de entrada da aplicação.
	 * @return Um objeto {@link Stock} representando o modelo de domínio.
	 */
	public static Stock toDomain(StockInput input) {
		return new Stock(input.quantity());
	}
}
