package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth;

import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AccessManagerSource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AuthenticatedUserSource;
import com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth.exceptions.AccessDeniedException;
import com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth.exceptions.NotAuthorizedException;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementação concreta: DataSource para validação de acesso com base no ID e
 * papel do usuário autenticado.
 */
@Component @Slf4j
public class DefaultAccessManagerSource implements AccessManagerSource {

	private final AuthenticatedUserSource userProvider;

	/**
	 * Construtor do serviço de validação de acesso.
	 *
	 * @param userProvider
	 *            Provedor de dados do usuário autenticado.
	 */
	public DefaultAccessManagerSource(AuthenticatedUserSource userProvider) {
		this.userProvider = userProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validateAccess(String userId) {
		String currentUserId = userProvider.getSubject();

		if (currentUserId == null || currentUserId.isEmpty()) {
			log.info("Negando acesso pois usuário não está autenticado, no recurso cujo dono é: {}", userId);
			throw new NotAuthorizedException("Usuário não autenticado.");
		}

		String role = userProvider.getRole();

		if (role == null || role.isEmpty()) {
			log.info(
					"Negando acesso para o usuário: {}, pela impossibilidade de determinar sua Role, no recurso cujo dono é: {}",
					currentUserId, userId);
			throw new AccessDeniedException("Impossível determinar a função do usuário.");
		}

		if (!currentUserId.equals(userId) && !"ADMIN".equalsIgnoreCase(role)) {
			log.info("Negando acesso para o usuário: {}, de Role: {}, no recurso cujo dono é: {}", currentUserId, role,
					userId);
			throw new AccessDeniedException("Você não tem permissão para visualizar este recurso.");
		}
	}
}
