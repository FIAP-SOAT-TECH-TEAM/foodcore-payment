package com.soat.fiap.food.core.payment.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.soat.fiap.food.core.payment.core.domain.exceptions.PaymentException;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.payment.core.domain.vo.QrCode;
import com.soat.fiap.food.core.shared.core.domain.exceptions.BusinessException;
import com.soat.fiap.food.core.shared.core.domain.vo.AuditInfo;

import lombok.Data;

/**
 * Entidade de domínio que representa um pagamento
 */
@Data
public class Payment {
	private UUID id;
	private String userId;
	private Long orderId;
	private PaymentMethod type;
	private LocalDateTime expiresIn = LocalDateTime.now().plusMinutes(30);
	private String tid;
	private BigDecimal amount;
	private QrCode qrCode;
	private PaymentStatus status = PaymentStatus.PENDING;
	private LocalDateTime paidAt;
	private String observations = "Pagamento via Mercado Pago";
	private AuditInfo auditInfo = new AuditInfo();

	/**
	 * Construtor que cria uma nova instância de pagamento com os dados fornecidos.
	 *
	 * @param userId
	 *            ID do usuário que realizou o pedido
	 * @param orderId
	 *            ID do pedido relacionado ao pagamento
	 * @param amount
	 *            Valor total do pagamento
	 * @throws NullPointerException
	 *             se type, expiresIn, tid ou amount forem nulos
	 */
	public Payment(String userId, Long orderId, BigDecimal amount) {

		validate(userId, orderId, amount);

		this.userId = userId;
		this.orderId = orderId;
		this.amount = amount;
	}

	/**
	 * Define o qrCode do pagamento.
	 */
	public void setQrCode(String qrCode) {
		this.qrCode = new QrCode(qrCode);
	}

	/**
	 * Define o status do pagamento.
	 */
	public void setStatus(PaymentStatus status) {
		if (status == PaymentStatus.APPROVED) {
			this.setPaidAt(LocalDateTime.now());
		}

		this.status = status;
		markUpdatedNow();
	}

	/**
	 * Define o transaction id do pagamento.
	 *
	 * @throws PaymentException
	 *             se o TID for maior que 255 caracteres {@code null}
	 */
	public void setTid(String tid) {
		if (tid != null && tid.length() > 255) {
			throw new PaymentException("O TID não pode ter mais de 255 caracteres");
		}

		this.tid = tid;
	}

	/**
	 * Valida os campos obrigatórios para um pagamento.
	 *
	 * @throws NullPointerException
	 *             se algum dos parâmetros for {@code null}
	 */
	private void validate(String userId, Long orderId, BigDecimal amount) {

		if (userId == null || userId.isEmpty()) {
			throw new PaymentException("O id do usuário não pode estar vazio");
		}

		Objects.requireNonNull(orderId, "O ID do pedido não pode ser nulo");
		Objects.requireNonNull(amount, "O valor total não pode ser nulo");
	}

	/**
	 * Retorna o nome do método de pagamento.
	 *
	 * @return o nome do método de pagamento
	 */
	public String getTypeName() {
		return this.type.name();
	}

	/**
	 * Atualiza o campo updatedAt com o horário atual.
	 *
	 * @throws BusinessException
	 *             se o horário atual for menor ou igual ao createdAt
	 */
	public void markUpdatedNow() {
		this.auditInfo.setUpdatedAt(LocalDateTime.now());
	}

}
