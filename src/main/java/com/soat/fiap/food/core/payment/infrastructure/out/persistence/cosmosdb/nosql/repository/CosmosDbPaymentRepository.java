package com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity.PaymentEntity;

/**
 * Repositório Cosmos DB para a entidade PaymentEntity
 */
@Repository
public interface CosmosDbPaymentRepository extends CosmosRepository<PaymentEntity, String> {

	/**
	 * Busca o último pagamento inserido para um determinado pedido
	 */
	Optional<PaymentEntity> findTopByOrderIdOrderByAuditInfoCreatedAtDesc(Long orderId);

	List<PaymentEntity> findByOrderId(Long orderId);

	/**
	 * Busca pagamentos não aprovados e expirados
	 */
	@Query("""
			SELECT * FROM c
			WHERE c.expiresIn < @now
			AND c.status NOT IN ('APPROVED', 'CANCELLED')
			""")
	List<PaymentEntity> getExpiredPaymentsWithoutApprovedOrCancelled(@Param("now") String now);
}
