package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.repository.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.ProductEntity;

/**
 * Repositório Spring Data JPA para ProductEntity
 */
@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {

	Optional<List<ProductEntity>> findByIdIn(List<Long> productIds);
}
