package com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.dto.PaymentDTO;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.dto.mappers.PaymentDTOMapper;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;

/**
 * Gateway para persistência do agregado Pagamento.
 */
public class PaymentGateway {

	private final PaymentDataSource paymentDataSource;

	public PaymentGateway(PaymentDataSource paymentDataSource) {
		this.paymentDataSource = paymentDataSource;
	}

	/**
	 * Salva o agregado Pagamento.
	 *
	 * @param payment
	 *            Agregado Pagamento a ser salvo
	 * @return Pagamento salvo com identificador atualizado
	 */
	public Payment save(Payment payment) {
		PaymentDTO dto = PaymentDTOMapper.toDTO(payment);
		PaymentDTO savedDTO = paymentDataSource.save(dto);
		return PaymentDTOMapper.toDomain(savedDTO);
	}

	/**
	 * Busca o pagamento mais recente de um pedido.
	 *
	 * @param orderId
	 *            ID do pedido
	 * @return Optional contendo o pagamento ou vazio se não encontrado
	 */
	public Optional<Payment> findTopByOrderIdOrderByIdDesc(Long orderId) {
		return paymentDataSource.findTopByOrderIdOrderByIdDesc(orderId).map(PaymentDTOMapper::toDomain);
	}

	/**
	 * Busca todos os pagamentos expirados que não foram aprovados nem cancelados.
	 *
	 * @param now
	 *            Data e hora atual para comparação
	 * @return Lista de pagamentos expirados
	 */
	public List<Payment> findExpiredPaymentsWithoutApprovedOrCancelled(LocalDateTime now) {
		return paymentDataSource.findExpiredPaymentsWithoutApprovedOrCancelled(now)
				.stream()
				.map(PaymentDTOMapper::toDomain)
				.toList();
	}

}
