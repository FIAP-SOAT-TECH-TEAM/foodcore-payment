package com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response;

import java.time.LocalDateTime;

import com.soat.fiap.food.core.payment.core.domain.vo.QrCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de obtenção do qrcode de um pedido
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Resposta de obtenção do QrCode de um pagamento")
public class QrCodeResponse {
	@Schema(description = "ID do pedido")
	private String orderId;
	@Schema(description = "Data de expiração do pagamento")
	private LocalDateTime expiresIn;
	@Schema(description = "Código QR do pedido")
	private QrCode qrCode;
}
