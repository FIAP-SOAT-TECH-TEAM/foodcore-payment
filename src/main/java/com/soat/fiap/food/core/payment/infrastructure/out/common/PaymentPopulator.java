package com.soat.fiap.food.core.payment.infrastructure.out.common;

import java.io.IOException;

/**
 * Interface para popular dados de pagamentos.
 * <p>
 * <b>Disclaimer:</b> Esta interface foi criada apenas para ser usada em
 * cenários onde não existem ferramentas oficiais de seeding disponíveis.
 */
public interface PaymentPopulator {

	/**
	 * Popula os dados de pagamento no repositório.
	 *
	 * @throws IOException
	 *             caso ocorra erros de leitura
	 */
	void populate() throws IOException;

	/**
	 * Remove todos os registros de pagamento do repositório.
	 */
	void reset();
}
