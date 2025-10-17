package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.soat.fiap.food.core.api.shared.core.domain.exceptions.BusinessException;
import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceConflictException;
import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth.exceptions.AccessDeniedException;
import com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth.exceptions.NotAuthorizedException;
import com.soat.fiap.food.core.api.shared.infrastructure.out.exceptions.APIException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler global de exceções
 */
@Slf4j @RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Trata exceções genéricas
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
		log.error("Erro não tratado: {}", ex.getMessage(), ex);

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage(), request.getServletPath());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Trata erros de validação
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		log.error("Erro de validação: {}", ex.getMessage());

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult()
				.getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(), "Erro de validação", request.getServletPath(), errors);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Trata erros de tamanho máximo de upload
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException ex,
			HttpServletRequest request) {

		log.error("Erro de tamanho de arquivo: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.PAYLOAD_TOO_LARGE.value(),
				"Tamanho máximo de arquivo excedido", request.getServletPath());

		return new ResponseEntity<>(errorResponse, HttpStatus.PAYLOAD_TOO_LARGE);
	}

	/**
	 * Trata erros de recurso não encontrado
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			HttpServletRequest request) {

		log.error("Recurso não encontrado: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				ex.getMessage(), request.getServletPath());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Trata erros de regra de negócio
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {

		log.error("Erro de regra de negócio: {}", ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				ex.getMessage(), request.getServletPath());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Trata erros de resposta de APIs
	 */
	@ExceptionHandler(APIException.class)
	public ResponseEntity<ErrorResponse> handleAPIException(APIException ex, HttpServletRequest request) {
		int statusCode = ex.getStatusCode();
		HttpStatus status = HttpStatus.resolve(statusCode);

		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			statusCode = status.value();
		}

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), statusCode, ex.getMessage(),
				request.getServletPath());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
	}

	/**
	 * Trata erros de integridade de dados (ex: violação de chave estrangeira)
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {

		log.error("Erro de integridade de dados: {}", ex.getMessage());

		var mensagem = "Operação não permitida: viola regras de integridade de dados";

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), mensagem,
				request.getServletPath());

		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

	/**
	 * Trata erros de formato inválido
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(HttpMessageNotReadableException ex,
			HttpServletRequest request) {

		log.error("Erro ao tentar desserializar: {}", ex.getMessage());

		String mensagem = "Erro de formato inválido na requisição";

		Throwable cause = ex.getCause();
		if (cause instanceof InvalidFormatException invalidFormatEx) {
			Class<?> targetType = invalidFormatEx.getTargetType();

			if (targetType.isEnum()) {
				String[] valoresAceitos = getEnumNames(targetType);
				mensagem = String.format("Valor inválido: %s. Valores aceitos para o campo: %s",
						invalidFormatEx.getValue(), String.join(", ", valoresAceitos));
			} else {
				mensagem = "Formato inválido para o campo: " + invalidFormatEx.getValue();
			}
		}

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), mensagem,
				request.getServletPath());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Trata erros de acesso inválido
	 */
	@ExceptionHandler(NotAuthorizedException.class)
	public ResponseEntity<ErrorResponse> handleNotAuthorizedException(NotAuthorizedException ex,
			HttpServletRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
				ex.getMessage(), request.getServletPath());

		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex,
			HttpServletRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(),
				ex.getMessage(), request.getServletPath());

		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}

	private String[] getEnumNames(Class<?> enumType) {
		Object[] enumConstants = enumType.getEnumConstants();
		String[] names = new String[enumConstants.length];
		for (int i = 0; i < enumConstants.length; i++) {
			names[i] = enumConstants[i].toString();
		}
		return names;
	}

	/**
	 * Trata erros de conflito de recursos (ex: CPF duplicado)
	 */
	@ExceptionHandler(ResourceConflictException.class)
	public ResponseEntity<ErrorResponse> handleResourceConflictException(ResourceConflictException ex,
			HttpServletRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				ex.getMessage(), request.getServletPath());

		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

}
