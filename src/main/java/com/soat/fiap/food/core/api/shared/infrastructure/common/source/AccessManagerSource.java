package com.soat.fiap.food.core.api.shared.infrastructure.common.source;

/**
 * Gerenciador de acesso para autenticação e autorização do usuário.
 */
public interface AccessManagerSource {

	/**
	 * Valida se o usuário autenticado tem permissão para acessar os dados de um
	 * usuário específico.
	 *
	 * @param userId
	 *            ID do usuário relacionado ao recurso sendo acessado.
	 * @throws com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth.exceptions.AccessDeniedException
	 *             se o acesso não for permitido.
	 */
	void validateAccess(String userId);
}
