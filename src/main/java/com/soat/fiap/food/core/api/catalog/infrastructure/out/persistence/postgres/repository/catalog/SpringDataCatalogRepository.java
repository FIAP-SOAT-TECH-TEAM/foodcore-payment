package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.repository.catalog;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.CatalogEntity;

/**
 * Repositório Spring Data JPA para CatalogEntity
 */
@Repository
public interface SpringDataCatalogRepository extends JpaRepository<CatalogEntity, Long> {

	Optional<CatalogEntity> findByName(String name);

	boolean existsByName(String name);

	boolean existsByNameAndIdNot(String name, Long id);

	@Query("SELECT COUNT(c) > 0 FROM CatalogEntity c JOIN c.categories cat WHERE c.id = :catalogId")
	boolean existsCategoryByCatalogId(@Param("catalogId") Long catalogId);

	@Query("SELECT c FROM CatalogEntity c JOIN c.categories cat JOIN cat.products p WHERE p.id = :productId")
	Optional<CatalogEntity> findByProductId(@Param("productId") Long productId);

}
