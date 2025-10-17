package com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.entity;

import static org.hibernate.type.SqlTypes.NAMED_ENUM;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;

import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.api.payment.core.domain.vo.QrCode;
import com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.converter.QrCodeConverter;
import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade JPA para pagamento
 */
@Entity @Table(name = "payments") @Getter @Setter
public class PaymentEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "order_id")
	private Integer orderId;

	@Enumerated(EnumType.STRING) @Column(nullable = false, columnDefinition = "payment_type_enum")
	@JdbcTypeCode(NAMED_ENUM)
	private PaymentMethod type;

	@Column(name = "expires_in", nullable = false)
	private LocalDateTime expiresIn;

	@Enumerated(EnumType.STRING) @Column(nullable = false, columnDefinition = "payment_status_enum")
	@JdbcTypeCode(NAMED_ENUM)
	private PaymentStatus status;

	@Column(nullable = true)
	private LocalDateTime paidAt;

	@Column(nullable = false, unique = true)
	private String tid;

	@Column(nullable = false)
	private BigDecimal amount;

	@Convert(converter = QrCodeConverter.class) @Column(name = "qr_code", length = 255, nullable = false, unique = true)
	private QrCode qrCode;

	@Column(columnDefinition = "TEXT")
	private String observations;

	@Embedded
	private AuditInfo auditInfo = new AuditInfo();
}
