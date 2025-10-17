package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.time.LocalDateTime;

/**
 * DTO utilizado para representar dados da entidade Stock. Serve como objeto de
 * transferência entre o domínio e o mundo externo (DataSource).
 */
public record StockDTO(Long id, Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
