package com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.azure.cosmos.models.CosmosPatchItemRequestOptions;
import com.azure.cosmos.models.CosmosPatchOperations;
import com.azure.cosmos.models.PartitionKey;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity.PaymentEntity;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.repository.CosmosDbPaymentRepository;

/**
 * Configuração do repositório CosmosDbPaymentRepository.
 * <p>
 **
 */
@Configuration
public class CosmosDbPaymentRepositoryConfig {

	/**
	 * Bean de fallback para {@link CosmosDbPaymentRepository}.
	 * <p>
	 * É registrado apenas se não houver outro bean do tipo
	 * CosmosDbPaymentRepository disponível no contexto Spring.
	 *
	 * @return uma implementação vazia de CosmosDbPaymentRepository, para fins de
	 *         teste ou tasks especificas
	 */
	@Bean @ConditionalOnMissingBean(CosmosDbPaymentRepository.class)
	public CosmosDbPaymentRepository CosmosDbPaymentRepository() {
		return new CosmosDbPaymentRepository() {

			@Override
			public Optional<PaymentEntity> findTopByOrderIdOrderByAuditInfoCreatedAtDesc(Long orderId) {
				return Optional.empty();
			}

			@Override
			public List<PaymentEntity> findByOrderId(Long orderId) {
				return List.of();
			}

			@Override
			public List<PaymentEntity> getExpiredPaymentsWithoutApprovedOrCancelled(String now) {
				return List.of();
			}

			@Override
			public Optional<PaymentEntity> findById(String s, PartitionKey partitionKey) {
				return Optional.empty();
			}

			@Override
			public void deleteById(String s, PartitionKey partitionKey) {
			}

			@Override
			public <S extends PaymentEntity> S save(String s, PartitionKey partitionKey, Class<S> domainType,
					CosmosPatchOperations patchOperations) {
				return null;
			}

			@Override
			public <S extends PaymentEntity> S save(String s, PartitionKey partitionKey, Class<S> domainType,
					CosmosPatchOperations patchOperations, CosmosPatchItemRequestOptions options) {
				return null;
			}

			@Override
			public Iterable<PaymentEntity> findAll(PartitionKey partitionKey) {
				return List.of();
			}

			@Override
			public <S extends PaymentEntity> S save(S entity) {
				return null;
			}

			@Override
			public <S extends PaymentEntity> Iterable<S> saveAll(Iterable<S> entities) {
				return List.of();
			}

			@Override
			public Optional<PaymentEntity> findById(String s) {
				return Optional.empty();
			}

			@Override
			public boolean existsById(String s) {
				return false;
			}

			@Override
			public Iterable<PaymentEntity> findAll() {
				return List.of();
			}

			@Override
			public Iterable<PaymentEntity> findAllById(Iterable<String> strings) {
				return List.of();
			}

			@Override
			public long count() {
				return 0;
			}

			@Override
			public void deleteById(String s) {
			}

			@Override
			public void delete(PaymentEntity entity) {
			}

			@Override
			public void deleteAllById(Iterable<? extends String> strings) {
			}

			@Override
			public void deleteAll(Iterable<? extends PaymentEntity> entities) {
			}

			@Override
			public void deleteAll() {
			}

			@Override
			public Iterable<PaymentEntity> findAll(Sort sort) {
				return List.of();
			}

			@Override
			public Page<PaymentEntity> findAll(Pageable pageable) {
				return Page.empty();
			}
		};
	}
}
