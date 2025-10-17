package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO utilizado para representar dados da entidade Catalog. Serve como objeto
 * de transferência entre o domínio e o mundo externo (DataSource).
 */
public record CatalogDTO(Long id, String name, List<CategoryDTO> categories, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
}
