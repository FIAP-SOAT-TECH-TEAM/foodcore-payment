package com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity.PaymentEntity;

/**
 * Repositório Cosmos DB para a entidade PaymentEntity
 */
@Repository
public interface CosmosDbPaymentRepository extends CosmosRepository<PaymentEntity, Long> {

	/**
	 * Busca o último pagamento inserido para um determinado pedido
	 */
	@Query("SELECT * FROM c WHERE c.orderId = orderId@ ORDER BY c._ts DESC OFFSET 0 LIMIT 1")
	Optional<PaymentEntity> findLatestByOrderId(Long orderId);

	/**
	 * Busca pagamentos não aprovados e expirados
	 */
	@Query("""
			SELECT * FROM c
			WHERE c.expiresIn < @now
			AND NOT (c.status IN ('APPROVED', 'CANCELLED'))
			""")
	List<PaymentEntity> findExpiredPaymentsWithoutApprovedOrCancelled(LocalDateTime now);
}
