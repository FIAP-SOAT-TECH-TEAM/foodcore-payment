package com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways;

import java.time.OffsetDateTime;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AuthenticatedUserSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Gateway para acessar informações do usuário autenticado no sistema.
 * <p>
 * Cada método expõe um atributo específico do usuário obtido via
 * {@link AuthenticatedUserSource}.
 * </p>
 */
@Slf4j
public class AuthenticatedUserGateway {

	private final AuthenticatedUserSource authenticatedUserSource;

	/**
	 * Construtor do gateway.
	 *
	 * @param authenticatedUserSource
	 *            fonte de dados do usuário autenticado
	 */
	public AuthenticatedUserGateway(AuthenticatedUserSource authenticatedUserSource) {
		this.authenticatedUserSource = authenticatedUserSource;
	}

	/**
	 * Retorna o identificador único (sub) do usuário autenticado.
	 *
	 * @return sub do usuário
	 */
	public String getSubject() {
		log.debug("Obtendo sub do usuário autenticado");
		return authenticatedUserSource.getSubject();
	}

	/**
	 * Retorna o nome completo do usuário autenticado.
	 *
	 * @return nome do usuário
	 */
	public String getName() {
		log.debug("Obtendo nome do usuário autenticado");
		return authenticatedUserSource.getName();
	}

	/**
	 * Retorna o email do usuário autenticado.
	 *
	 * @return email do usuário
	 */
	public String getEmail() {
		log.debug("Obtendo email do usuário autenticado");
		return authenticatedUserSource.getEmail();
	}

	/**
	 * Retorna o CPF do usuário autenticado.
	 *
	 * @return CPF do usuário
	 */
	public String getCpf() {
		log.debug("Obtendo CPF do usuário autenticado");
		return authenticatedUserSource.getCpf();
	}

	/**
	 * Retorna a role do usuário autenticado.
	 *
	 * @return role do usuário
	 */
	public String getRole() {
		log.debug("Obtendo role do usuário autenticado");
		return authenticatedUserSource.getRole();
	}

	/**
	 * Retorna a data de criação do usuário autenticado.
	 *
	 * @return data de criação do usuário
	 */
	public OffsetDateTime getCreationDate() {
		log.debug("Obtendo data de criação do usuário autenticado");
		return authenticatedUserSource.getCreationDate();
	}
}
