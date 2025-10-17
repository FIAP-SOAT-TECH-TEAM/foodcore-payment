package com.soat.fiap.food.core.payment.core.interfaceadapters.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.soat.fiap.food.core.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;

/**
 * DTO utilizado para representar dados da entidade Payment. Serve como objeto
 * de transferência entre o domínio e o mundo externo (DataSource).
 */
public record PaymentDTO(Long id, String userId, Long orderId, PaymentMethod type, LocalDateTime expiresIn, String tid,
		BigDecimal amount, String qrCode, PaymentStatus status, LocalDateTime paidAt, String observations,
		LocalDateTime createdAt, LocalDateTime updatedAt) {
}
