package com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.payment.core.domain.vo.QrCode;
import com.soat.fiap.food.core.shared.core.domain.vo.AuditInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade para pagamento armazenada no Azure Cosmos DB
 */
@Getter @Setter @Container(containerName = "payments")
public class PaymentEntity {

	@Id @GeneratedValue
	private String id;

	@PartitionKey
	private String userId;

	private Long orderId;

	private PaymentMethod type;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDateTime expiresIn;

	private PaymentStatus status;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDateTime paidAt;

	private String tid;

	private BigDecimal amount;

	private QrCode qrCode;

	private String observations;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private AuditInfo auditInfo = new AuditInfo();
}
