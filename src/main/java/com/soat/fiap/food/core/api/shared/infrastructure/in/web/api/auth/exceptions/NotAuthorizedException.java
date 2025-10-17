package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth.exceptions;

/**
 * Exceção lançada quando um usuário tenta acessar um recurso ou executar uma
 * operação sem estar autenticado.
 * <p>
 * Esta exceção é verificada em tempo de execução e geralmente está associada a
 * falhas de autenticação/autorização no contexto de segurança da aplicação.
 * </p>
 *
 * <p>
 * <b>Exemplo de uso:</b>
 * </p>
 *
 * <pre>{@code
 * if (!usuario.temPermissao(recurso)) {
 * 	throw new NotAuthorizedException("Usuário não autenticado.");
 * }
 * }</pre>
 *
 */
public class NotAuthorizedException extends RuntimeException {

	/**
	 * Cria uma nova instância da exceção com a mensagem detalhando o motivo da não
	 * autorização.
	 *
	 * @param message
	 *            mensagem explicativa da exceção, geralmente descrevendo qual
	 *            recurso ou operação foi não autorizada.
	 */
	public NotAuthorizedException(String message) {
		super(message);
	}
}
