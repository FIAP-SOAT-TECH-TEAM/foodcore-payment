package com.soat.fiap.food.core.payment.core.interfaceadapters.gateways;

import java.time.LocalDateTime;

import com.soat.fiap.food.core.payment.core.application.inputs.StockDebitInput;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.AcquirerPaymentDTO;
import com.soat.fiap.food.core.payment.infrastructure.common.source.AcquirerSource;

/**
 * Gateway para comunicação com o adquirente de pagamentos.
 * <p>
 * Atua como intermediário entre a aplicação e o {@link AcquirerSource},
 * responsável por gerar QR Codes e consultar informações de pedidos e
 * pagamentos junto ao adquirente.
 */
public class AcquirerGateway {

	private final AcquirerSource acquirerSource;

	public AcquirerGateway(AcquirerSource acquirerSource) {
		this.acquirerSource = acquirerSource;
	}

	/**
	 * Gera um QR Code de pagamento a partir dos dados fornecidos.
	 *
	 * @param input
	 *            Requisição com dados necessários para gerar o QR Code.
	 * @param expireIn
	 *            Data de expiração do qrCode.
	 * @return o QR Code gerado.
	 */
	public String generateQrCode(StockDebitInput input, LocalDateTime expireIn) {
		return acquirerSource.generateQrCode(input, expireIn);
	}

	/**
	 * Consulta os pagamentos realizados no adquirente usando o ID informado.
	 *
	 * @param id
	 *            Identificador do pagamento no adquirente.
	 * @return Resposta com os detalhes dos pagamentos.
	 */
	public AcquirerPaymentDTO getAcquirerPayments(String id) {
		return acquirerSource.getAcquirerPayments(id);
	}

	/**
	 * Consulta os detalhes de um pedido no adquirente.
	 *
	 * @param orderId
	 *            ID do pedido a ser consultado.
	 * @return Resposta bruta com os detalhes do pedido.
	 */
	public Object getAcquirerOrder(Long orderId) {
		return acquirerSource.getAcquirerOrder(orderId);
	}
}
