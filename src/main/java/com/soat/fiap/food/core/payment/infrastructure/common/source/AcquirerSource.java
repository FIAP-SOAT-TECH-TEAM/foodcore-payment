package com.soat.fiap.food.core.payment.infrastructure.common.source;

import java.time.LocalDateTime;

import com.soat.fiap.food.core.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.AcquirerPaymentDTO;

/**
 * DataSource para comunicação com o adquirente
 */
public interface AcquirerSource {
	/**
	 * Gera um QR Code para pagamento com base na requisição fornecida.
	 *
	 * @param input
	 *            Objeto contendo os dados necessários para gerar o QR Code.
	 * @param expireIn
	 *            Data de expiração do qrCode.
	 */
	String generateQrCode(OrderCreatedInput input, LocalDateTime expireIn);

	/**
	 * Consulta os pagamentos do adquirente pelo ID informado.
	 *
	 * @param id
	 *            Identificador do pagamento.
	 */
	AcquirerPaymentDTO getAcquirerPayments(String id);

	/**
	 * Consulta um pedido (order) do adquirente pelo seu ID.
	 *
	 * @param orderId
	 *            Identificador do pedido.
	 */
	Object getAcquirerOrder(Long orderId);
}
