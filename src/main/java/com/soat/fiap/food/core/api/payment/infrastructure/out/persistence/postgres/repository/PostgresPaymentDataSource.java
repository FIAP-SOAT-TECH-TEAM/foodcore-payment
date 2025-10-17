package com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.api.payment.core.interfaceadapters.dto.PaymentDTO;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.entity.PaymentEntity;
import com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.mapper.PaymentEntityMapper;

/**
 * Implementação concreta: DataSource para persistência do agregado Pagamento.
 */
@Component
public class PostgresPaymentDataSource implements PaymentDataSource {

	private final SpringDataPaymentRepository repository;
	private final PaymentEntityMapper mapper;

	public PostgresPaymentDataSource(SpringDataPaymentRepository repository, PaymentEntityMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override @Transactional
	public PaymentDTO save(PaymentDTO paymentDTO) {
		PaymentEntity entity = mapper.toEntity(paymentDTO);
		PaymentEntity savedEntity = repository.save(entity);
		return mapper.toDTO(savedEntity);
	}

	@Override @Transactional(readOnly = true)
	public Optional<PaymentDTO> findTopByOrderIdOrderByIdDesc(Long orderId) {
		return repository.findTopByOrderIdOrderByIdDesc(orderId).map(mapper::toDTO);
	}

	@Override @Transactional(readOnly = true)
	public boolean existsByOrderId(Long orderId) {
		return repository.existsByOrderId(orderId);
	}

	@Override @Transactional(readOnly = true)
	public List<PaymentDTO> findExpiredPaymentsWithoutApprovedOrCancelled(LocalDateTime now) {
		var paymentEntities = repository.findExpiredPaymentsWithoutApprovedOrCancelled(now);

		return paymentEntities.stream().map(mapper::toDTO).toList();
	}
}
