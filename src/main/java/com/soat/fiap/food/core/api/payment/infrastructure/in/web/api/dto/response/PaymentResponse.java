package com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de resposta para pagamentos
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor @Schema(description = "Resposta com dados de um pagamento")
public class PaymentResponse {

	@Schema(description = "ID do pagamento", example = "1")
	private Long id;

	@Schema(description = "ID do pedido associado", example = "1001")
	private Long orderId;

	@Schema(description = "ID do usuário que realizou o pagamento", example = "user-123")
	private String userId;

	@Schema(description = "Método de pagamento", example = "PIX")
	private PaymentMethod type;

	@Schema(description = "Nome do método de pagamento", example = "PIX")
	private String typeName;

	@Schema(description = "Status do pagamento", example = "APPROVED")
	private PaymentStatus status;

	@Schema(description = "Descrição do status do pagamento", example = "Pagamento aprovado com sucesso")
	private String statusDescription;

	@Schema(description = "Identificador de transação (TID)", example = "TID-99887766")
	private String tid;

	@Schema(description = "Valor total do pagamento", example = "75.90")
	private BigDecimal amount;

	@Schema(description = "Código QR para pagamento", example = "00020126360014BR.GOV.BCB.PIX0114+554799999999...")
	private String qrCode;

	@Schema(description = "Data e hora de expiração do pagamento", example = "2023-06-15T15:00:00")
	private LocalDateTime expiresIn;

	@Schema(description = "Data e hora em que o pagamento foi efetuado", example = "2023-06-15T14:45:22")
	private LocalDateTime paidAt;

	@Schema(description = "Observações sobre o pagamento", example = "Pagamento via Mercado Pago")
	private String observations;

	@Schema(description = "Data de criação", example = "2023-06-15T14:30:15")
	private LocalDateTime createdAt;

	@Schema(description = "Data de atualização", example = "2023-06-15T14:45:22")
	private LocalDateTime updatedAt;
}
