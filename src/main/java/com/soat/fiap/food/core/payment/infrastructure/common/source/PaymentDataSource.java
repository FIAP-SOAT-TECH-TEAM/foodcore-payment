package com.soat.fiap.food.core.payment.infrastructure.common.source;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.PaymentDTO;

/**
 * DataSource para persistência de pagamentos
 */
public interface PaymentDataSource {

	/**
	 * Salva um pagamento no repositório
	 *
	 * @param dto
	 *            DTO do pagamento a ser salvo
	 * @return Pagamento salvo com ID gerado
	 */
	PaymentDTO save(PaymentDTO dto);

	/**
	 * Busca o último pagamento inserido para um determinado pedido
	 *
	 * @param orderId
	 *            ID do pedido
	 * @return Pagamento encontrado ou vazio
	 */
	Optional<PaymentDTO> findLatestByOrderId(Long orderId);

	/**
	 * Busca pagamentos não aprovados expirados
	 *
	 * @param now
	 *            {@code LocalDateTime} com o horário atual
	 * @return Lista de pagamento expirados
	 */
	List<PaymentDTO> findExpiredPaymentsWithoutApprovedOrCancelled(LocalDateTime now);
}
