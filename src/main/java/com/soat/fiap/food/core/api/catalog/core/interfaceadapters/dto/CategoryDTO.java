package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;

/**
 * DTO utilizado para representar dados da entidade Category. Serve como objeto
 * de transferência entre o domínio e o mundo externo (DataSource).
 */
public record CategoryDTO(Long id, Details details, String imageUrl, int displayOrder, boolean active,
		List<ProductDTO> products, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
