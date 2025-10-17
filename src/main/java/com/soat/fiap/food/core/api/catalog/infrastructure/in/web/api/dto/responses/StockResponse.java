package com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respostas de estoque
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Schema(name = "StockResponse", description = "DTO para resposta com informações de estoque")
public class StockResponse {
	@Schema(description = "ID do estoque", example = "2001")
	private Long id;

	@Schema(description = "Quantidade disponível em estoque", example = "15")
	private Integer quantity;

	@Schema(description = "Data de criação", example = "2024-01-01T10:00:00")
	private LocalDateTime createdAt;

	@Schema(description = "Data da última atualização", example = "2024-01-02T11:30:00")
	private LocalDateTime updatedAt;
}
