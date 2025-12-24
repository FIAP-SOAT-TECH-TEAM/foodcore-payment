package com.soat.fiap.food.core.payment.infrastructure.out.common;

import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Inicializador do seeder de pagamentos.
 * <p>
 * Executa automaticamente a população da coleção de pagamentos ao iniciar a
 * aplicação".
 */
@Component @Transactional @Profile("local") @RequiredArgsConstructor @ConditionalOnBean(PaymentPopulator.class)
public class SeederInitializer {

	/** Componente responsável por popular e gerenciar os dados de pagamento */
	private final PaymentPopulator populator;

	/**
	 * Executa a população dos dados após a construção do bean.
	 *
	 * @throws IOException
	 *             caso ocorra erro ao ler o arquivo JSON
	 */
	@PostConstruct
	public void execute() throws IOException {
		populator.populate();
	}
}
