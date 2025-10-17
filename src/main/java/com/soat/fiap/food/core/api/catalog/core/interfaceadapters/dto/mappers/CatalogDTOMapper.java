package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import java.util.List;
import java.util.Objects;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CatalogDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CategoryDTO;
import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

/**
 * Mapper responsável por mapear entre a entidade de domínio Catalog e seu
 * correspondente DTO.
 */
public class CatalogDTOMapper {

	/**
	 * Converte um CatalogDTO para um objeto Catalog.
	 *
	 * @param dto
	 *            o CatalogDTO a ser convertido
	 * @return o objeto Catalog correspondente
	 */
	public static Catalog toDomain(CatalogDTO dto) {
		Objects.requireNonNull(dto, "O DTO do catálogo não pode ser nulo");

		Catalog catalog = new Catalog(dto.name());
		catalog.setId(dto.id());

		if (dto.categories() != null) {
			for (CategoryDTO categoryDTO : dto.categories()) {
				catalog.addCategory(CategoryDTOMapper.toDomain(categoryDTO));
			}
		}

		if (dto.createdAt() != null && dto.updatedAt() != null) {
			catalog.setAuditInfo(new AuditInfo(dto.createdAt(), dto.updatedAt()));
		}

		return catalog;
	}

	/**
	 * Converte um objeto Catalog para um CatalogDTO.
	 *
	 * @param catalog
	 *            o objeto Catalog a ser convertido
	 * @return o CatalogDTO correspondente
	 */
	public static CatalogDTO toDTO(Catalog catalog) {
		List<CategoryDTO> categoryDTOs = catalog.getCategories().stream().map(CategoryDTOMapper::toDTO).toList();

		return new CatalogDTO(catalog.getId(), catalog.getName(), categoryDTOs, catalog.getCreatedAt(),
				catalog.getUpdatedAt());
	}
}
