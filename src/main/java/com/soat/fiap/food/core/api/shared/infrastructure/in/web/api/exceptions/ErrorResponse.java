package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.exceptions;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Resposta de erro padrão
 */
@Getter @AllArgsConstructor @Schema(description = "Modelo de resposta padrão para erros da API")
public class ErrorResponse {

	@Schema(description = "Momento em que o erro ocorreu", example = "2023-06-15T14:45:22")
	private final LocalDateTime timestamp;

	@Schema(description = "Código HTTP do erro", example = "404")
	private final int status;

	@Schema(description = "Descrição do erro ocorrido", example = "Recurso não encontrado")
	private final String message;

	@Schema(description = "Caminho da requisição onde o erro ocorreu", example = "/api/v1/orders/123")
	private final String path;
}
