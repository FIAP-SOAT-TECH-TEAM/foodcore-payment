package com.soat.fiap.food.core.payment.core.interfaceadapters.dto.mappers;

import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.PaymentDTO;
import com.soat.fiap.food.core.payment.shared.core.domain.vo.AuditInfo;

/**
 * Mapper responsável por mapear entre a entidade de domínio Payment e seu
 * correspondente DTO.
 */
public class PaymentDTOMapper {

	/**
	 * Cria uma instância de {@link Payment} a partir de um {@link PaymentDTO}.
	 *
	 * @param dto
	 *            DTO de pagamento contendo os dados a serem convertidos
	 * @return Instância da entidade de domínio {@link Payment}
	 */
	public static Payment toDomain(PaymentDTO dto) {

		Payment payment = new Payment(dto.userId(), dto.orderId(), dto.amount());
		payment.setId(dto.id());
		payment.setType(dto.type());
		payment.setExpiresIn(dto.expiresIn());
		payment.setTid(dto.tid());
		payment.setQrCode(dto.qrCode());
		payment.setStatus(dto.status());
		payment.setPaidAt(dto.paidAt());
		payment.setObservations(dto.observations());
		payment.setAuditInfo(new AuditInfo(dto.createdAt(), dto.updatedAt()));
		return payment;
	}

	/**
	 * Cria uma instância de {@link PaymentDTO} a partir de um {@link Payment}.
	 *
	 * @param payment
	 *            Entidade de domínio de pagamento contendo os dados a serem
	 *            convertidos
	 * @return Instância do DTO de pagamento {@link PaymentDTO}
	 */
	public static PaymentDTO toDTO(Payment payment) {
		return new PaymentDTO(payment.getId(), payment.getUserId(), payment.getOrderId(), payment.getType(),
				payment.getExpiresIn(), payment.getTid(), payment.getAmount(),
				payment.getQrCode() != null ? payment.getQrCode().value() : null, payment.getStatus(),
				payment.getPaidAt(), payment.getObservations(), payment.getAuditInfo().getCreatedAt(),
				payment.getAuditInfo().getUpdatedAt());
	}

}
