package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AuthenticatedUserSource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação de {@link AuthenticatedUserSource} que extrai as informações do
 * usuário autenticado a partir dos headers HTTP da requisição atual.
 * <p>
 * Os headers são definidos no API Management, após a autenticação do usuário, e
 * propagados para o backend.
 * </p>
 */
@Component @Slf4j
public class DefaultAuthenticatedUserSource implements AuthenticatedUserSource {

	private HttpServletRequest getCurrentRequest() {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		if (attrs instanceof ServletRequestAttributes servletAttrs) {
			return servletAttrs.getRequest();
		}

		throw new IllegalStateException("Nenhuma requisição HTTP ativa encontrada.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSubject() {
		return getCurrentRequest().getHeader("Auth-Subject");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return getCurrentRequest().getHeader("Auth-Name");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEmail() {
		return getCurrentRequest().getHeader("Auth-Email");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCpf() {
		return getCurrentRequest().getHeader("Auth-Cpf");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRole() {
		return getCurrentRequest().getHeader("Auth-Role");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OffsetDateTime getCreationDate() {
		String createdAt = getCurrentRequest().getHeader("Auth-CreatedAt");

		if (createdAt == null || createdAt.startsWith("0001-01-01")) {
			return null;
		}

		try {
			return OffsetDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME);
		} catch (DateTimeParseException e) {
			log.error("Erro ao parsear data '{}': {}", createdAt, e.getMessage());
			return null;
		}
	}

}
