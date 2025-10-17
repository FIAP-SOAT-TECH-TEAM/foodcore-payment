package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Resposta de erro de validação
 */
@Getter @Schema(description = "Modelo de resposta para erros de validação de campos")
public class ValidationErrorResponse extends ErrorResponse {

	@Schema(description = "Lista de erros de validação, onde a chave é o nome do campo e o valor é a mensagem de erro", example = "{\"email\": \"Formato inválido\", \"senha\": \"Deve conter pelo menos 8 caracteres\"}")
	private final Map<String, String> errors;

	public ValidationErrorResponse(
			@Schema(description = "Momento em que o erro ocorreu", example = "2023-06-15T14:45:22") LocalDateTime timestamp,
			@Schema(description = "Código HTTP do erro", example = "400") int status,
			@Schema(description = "Descrição do erro ocorrido", example = "Erro de validação nos campos da requisição") String message,
			@Schema(description = "Caminho da requisição onde o erro ocorreu", example = "/api/v1/users") String path,
			Map<String, String> errors) {
		super(timestamp, status, message, path);
		this.errors = errors;
	}
}
