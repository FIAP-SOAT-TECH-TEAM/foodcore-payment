package com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.PaymentDTO;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity.PaymentEntity;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.mapper.PaymentEntityMapper;

/**
 * Implementação concreta: DataSource para persistência do agregado Pagamento.
 */
@Component
public class CosmosDbPaymentDataSource implements PaymentDataSource {

	private final CosmosDbPaymentRepository repository;
	private final PaymentEntityMapper mapper;

	public CosmosDbPaymentDataSource(CosmosDbPaymentRepository repository, PaymentEntityMapper mapper) {
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
	public Optional<PaymentDTO> findLatestByOrderId(Long orderId) {
		return repository.findTopByOrderIdOrderByAuditInfoCreatedAtDesc(orderId).map(mapper::toDTO);
	}

	@Override @Transactional(readOnly = true)
	public List<PaymentDTO> findExpiredPaymentsWithoutApprovedOrCancelled(LocalDateTime now) {
		var paymentEntities = repository.getExpiredPaymentsWithoutApprovedOrCancelled(now);
		return paymentEntities.stream().map(mapper::toDTO).toList();
	}

	@Override @Transactional(readOnly = true)
	public List<PaymentDTO> findByOrderId(Long orderId) {
		var paymentEntities = repository.findByOrderId(orderId);
		return paymentEntities.stream().map(mapper::toDTO).toList();
	}
}
