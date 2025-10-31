package com.soat.fiap.food.core.payment.core.application.inputs;

import java.math.BigDecimal;
import java.util.List;

/**
 * Representa um DTO de entrada da aplicação (Application Layer) contendo os
 * dados de débito de estoque.
 * <p>
 * Este input é usado pelo caso de uso {@code InitializePaymentUseCase}.
 *
 * @param orderId
 *            Id do pedido que originou o débito de estoque
 * @param orderNumber
 *            Número do pedido que originou o débito de estoque
 * @param userId
 *            Id do usuário que originou o débito de estoque
 * @param totalAmount
 *            Total do pedido que originou o débito de estoque
 * @param items
 *            Lista de itens debitados do estoque
 */
public record StockDebitInput(Long orderId, String orderNumber, String userId, BigDecimal totalAmount,
		List<StockDebitItemInput> items) {
	/**
	 * Construtor para inicializar um {@code StockDebitInput}.
	 *
	 * @param orderId
	 *            Id do pedido que originou o débito de estoque
	 * @param orderNumber
	 *            Número do pedido que originou o débito de estoque
	 * @param userId
	 *            Id do usuário que originou o débito de estoque
	 * @param totalAmount
	 *            Total do pedido que originou o débito de estoque
	 */
	public StockDebitInput {
	}
}
