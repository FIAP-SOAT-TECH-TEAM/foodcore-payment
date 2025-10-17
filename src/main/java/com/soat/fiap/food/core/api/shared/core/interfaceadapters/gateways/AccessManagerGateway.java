package com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AccessManagerSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Gateway para validação de acesso com base no ID do usuário autenticado.
 */
@Slf4j
public class AccessManagerGateway {

	private final AccessManagerSource accessManagerSource;

	public AccessManagerGateway(AccessManagerSource accessManagerSource) {
		this.accessManagerSource = accessManagerSource;
	}

	/**
	 * Verifica se o usuário autenticado tem permissão para acessar os dados do
	 * usuário informado.
	 *
	 * @param userId
	 *            ID do usuário que está sendo acessado.
	 * @throws com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth.exceptions.AccessDeniedException
	 *             se o usuário não tiver permissão.
	 */
	public void validateAccess(String userId) {
		log.debug("Validando acesso para o usuário com ID: {}", userId);
		accessManagerSource.validateAccess(userId);
	}
}
