package com.soat.fiap.food.core.api.payment.core.application.inputs;

import java.math.BigDecimal;
import java.util.List;

/**
 * Representa um DTO de entrada da aplicação (Application Layer) contendo os
 * dados necessários para inicializar um pagamento.
 * <p>
 * Este input é usado pelo caso de uso {@code InitializePaymentUseCase}.
 *
 * @param orderId
 *            ID do pedido.
 * @param orderNumber
 *            Número identificador do pedido.
 * @param userId
 *            ID do usuário que realizou o pedido.
 * @param totalAmount
 *            Valor total do pedido.
 * @param items
 *            Lista de itens do pedido.
 */
public record OrderCreatedInput(Long orderId, String orderNumber, String userId, BigDecimal totalAmount,
		List<OrderCreatedItemInput> items) {
	/**
	 * Construtor para inicializar um {@code OrderCreatedInput}.
	 *
	 * @param orderId
	 *            ID do pedido.
	 * @param orderNumber
	 *            Número do pedido.
	 * @param userId
	 *            ID do usuário.
	 * @param totalAmount
	 *            Valor total do pedido.
	 * @param items
	 *            Lista de itens do pedido.
	 */
	public OrderCreatedInput {
	}

	/**
	 * Representa um item do pedido incluído na criação do pagamento.
	 *
	 * @param productId
	 *            ID do produto.
	 * @param name
	 *            Nome do produto.
	 * @param quantity
	 *            Quantidade do produto no pedido.
	 * @param unitPrice
	 *            Preço unitário.
	 * @param subtotal
	 *            Subtotal (unitário × quantidade).
	 * @param observations
	 *            Observações adicionais do item.
	 */
	public record OrderCreatedItemInput(Long productId, String name, Integer quantity, BigDecimal unitPrice,
			BigDecimal subtotal, String observations) {
		/**
		 * Construtor para {@code OrderItemInput}.
		 *
		 * @param productId
		 *            ID do produto.
		 * @param name
		 *            Nome do produto.
		 * @param quantity
		 *            Quantidade.
		 * @param unitPrice
		 *            Preço unitário.
		 * @param subtotal
		 *            Subtotal.
		 * @param observations
		 *            Observações.
		 */
		public OrderCreatedItemInput {
		}
	}
}
