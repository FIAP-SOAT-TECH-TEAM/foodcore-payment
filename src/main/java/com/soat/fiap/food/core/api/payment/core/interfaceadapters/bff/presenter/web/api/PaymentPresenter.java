package com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.presenter.web.api;

import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.PaymentResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.PaymentStatusResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.QrCodeResponse;

/**
 * Presenter responsável por converter objetos do domínio {@link Payment} em
 * objetos de resposta {@link PaymentStatusResponse} utilizados na camada de API
 * web (web.api).
 */
public class PaymentPresenter {

	/**
	 * Converte uma instância da entidade {@link Payment} para um
	 * {@link PaymentStatusResponse}.
	 *
	 * @param payment
	 *            A entidade de domínio {@link Payment} a ser convertida.
	 * @return Um DTO {@link PaymentStatusResponse} com os dados do status de
	 *         pagamento.
	 */
	public static PaymentStatusResponse toPaymentStatusResponse(Payment payment) {
		return PaymentStatusResponse.builder().orderId(payment.getOrderId()).status(payment.getStatus()).build();
	}

	/**
	 * Converte uma instância da entidade {@link Payment} para um
	 * {@link QrCodeResponse}.
	 *
	 * @param payment
	 *            A entidade de domínio {@link Payment} contendo o QR Code e dados
	 *            de expiração.
	 * @return Um DTO {@link QrCodeResponse} com os dados formatados para resposta
	 *         HTTP.
	 */
	public static QrCodeResponse toQrCodeResponse(Payment payment) {
		return QrCodeResponse.builder()
				.orderId(String.valueOf(payment.getOrderId()))
				.expiresIn(payment.getExpiresIn())
				.qrCode(payment.getQrCode())
				.build();
	}

	/**
	 * Converte uma instância da entidade {@link Payment} para um
	 * {@link PaymentResponse}.
	 *
	 * @param payment
	 *            A entidade de domínio {@link Payment} a ser convertida.
	 * @return Um DTO {@link PaymentResponse} contendo todos os dados relevantes do
	 *         pagamento.
	 */
	public static PaymentResponse toPaymentResponse(Payment payment) {
		if (payment == null) {
			return null;
		}

		return PaymentResponse.builder()
				.id(payment.getId())
				.orderId(payment.getOrderId())
				.userId(payment.getUserId())
				.type(payment.getType())
				.typeName(payment.getType() != null ? payment.getType().name() : null)
				.status(payment.getStatus())
				.statusDescription(payment.getStatus() != null ? payment.getStatus().getDescription() : null)
				.tid(payment.getTid())
				.amount(payment.getAmount())
				.qrCode(payment.getQrCode() != null ? payment.getQrCode().value() : null)
				.expiresIn(payment.getExpiresIn())
				.paidAt(payment.getPaidAt())
				.observations(payment.getObservations())
				.createdAt(payment.getAuditInfo().getCreatedAt())
				.updatedAt(payment.getAuditInfo().getUpdatedAt())
				.build();
	}
}
