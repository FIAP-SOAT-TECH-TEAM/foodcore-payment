package com.soat.fiap.food.core.api.shared.infrastructure.common.source;

import java.time.OffsetDateTime;

/**
 * Fornece informações do usuário autenticado no sistema.
 * <p>
 * Esta interface expõe os dados principais que identificam e caracterizam o
 * usuário logado, de acordo com os atributos obtidos via Amazon Cognito ou
 * outro provedor de identidade configurado.
 * </p>
 */
public interface AuthenticatedUserSource {

	/**
	 * Retorna o identificador único do usuário (sub claim do JWT).
	 *
	 * @return o identificador único do usuário
	 */
	String getSubject();

	/**
	 * Retorna o nome completo do usuário.
	 *
	 * @return o nome completo do usuário
	 */
	String getName();

	/**
	 * Retorna o email do usuário.
	 *
	 * @return o email do usuário
	 */
	String getEmail();

	/**
	 * Retorna o CPF do usuário.
	 * <p>
	 * Este valor geralmente é armazenado como atributo customizado no Cognito.
	 * </p>
	 *
	 * @return o CPF do usuário
	 */
	String getCpf();

	/**
	 * Retorna a role (papel) associada ao usuário.
	 * <p>
	 * Esse atributo define permissões e escopos de acesso na aplicação.
	 * </p>
	 *
	 * @return a role do usuário
	 */
	String getRole();

	/**
	 * Retorna a data e hora de criação do usuário no provedor de identidade, no
	 * formato ISO 8601
	 *
	 * @return a data e hora de criação do usuário
	 */
	OffsetDateTime getCreationDate();
}
