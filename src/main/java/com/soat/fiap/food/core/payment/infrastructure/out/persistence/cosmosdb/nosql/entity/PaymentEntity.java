package com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
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
	private Long id;

	@PartitionKey
	private String userId;

	private Integer orderId;

	private PaymentMethod type;

	private LocalDateTime expiresIn;

	private PaymentStatus status;

	private LocalDateTime paidAt;

	private String tid;

	private BigDecimal amount;

	private QrCode qrCode;

	private String observations;

	private AuditInfo auditInfo = new AuditInfo();

	@Version
	private String eTag;
}
