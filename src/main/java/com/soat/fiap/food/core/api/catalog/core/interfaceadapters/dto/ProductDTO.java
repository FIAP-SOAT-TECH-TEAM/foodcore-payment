package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;

/**
 * DTO utilizado para representar dados da entidade Product. Serve como objeto
 * de transferência entre o domínio e o mundo externo (DataSource).
 */
public record ProductDTO(Long id, Details details, String imageUrl, BigDecimal price, StockDTO stock,
		CategoryDTO category, Integer displayOrder, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
