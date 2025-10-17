package com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response;

import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de checagem de pagamento
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Resposta de checagem do status de pagamento")
public class PaymentStatusResponse {
	@Schema(description = "ID do pedido")
	private Long orderId;
	@Schema(description = "Status de pagamento do pedido")
	private PaymentStatus status;
}
