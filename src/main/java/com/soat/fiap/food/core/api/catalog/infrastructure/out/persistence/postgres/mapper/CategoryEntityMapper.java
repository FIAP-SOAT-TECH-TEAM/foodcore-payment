package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CategoryDTO;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.CategoryEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper.shared.ImageURLMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;

/**
 * Mapper que converte entre a entidade de domínio Category e a entidade JPA
 * CategoryEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductEntityMapper.class,
		ImageURLMapper.class})
public interface CategoryEntityMapper {

	/**
	 * Converte uma entidade JPA para um DTO.
	 *
	 * @param entity
	 *            Entidade JPA
	 * @return DTO correspondente
	 */
	@Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "mapImageUrlToString")
	@Mapping(source = "auditInfo.createdAt", target = "createdAt")
	@Mapping(source = "auditInfo.updatedAt", target = "updatedAt")
	CategoryDTO toDTO(CategoryEntity entity);
	/**
	 * Converte uma entidade JPA para uma entidade de domínio
	 *
	 * @param entity
	 *            Entidade JPA
	 * @param cycleAvoidingMappingContext
	 *            Contexto para evitar ciclos
	 * @return Entidade de domínio
	 */
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.AuditInfoMapper.buildAuditInfo(entity.getAuditInfo().getCreatedAt(), entity.getAuditInfo().getUpdatedAt()))")
	Category toDomain(CategoryEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	/**
	 * Converte uma lista de entidades JPA para uma lista de entidades de domínio
	 *
	 * @param entities
	 *            Lista de entidades JPA
	 * @param cycleAvoidingMappingContext
	 *            Contexto para evitar ciclos
	 * @return Lista de entidades de domínio
	 */
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.AuditInfoMapper.buildAuditInfo(entities.getAuditInfo().getCreatedAt(), entities.getAuditInfo().getUpdatedAt()))")
	List<Category> toDomainList(List<CategoryEntity> entities,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	/**
	 * Converte uma entidade de domínio para uma entidade JPA
	 *
	 * @param dto
	 *            DTO da categoria
	 * @return Entidade JPA
	 */
	@Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "mapStringToImageUrl")
	@Mapping(target = "catalog", ignore = true)
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.AuditInfoMapper.buildAuditInfo(dto.createdAt(), dto.updatedAt()))")
	CategoryEntity toEntity(CategoryDTO dto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	@DoIgnore
	default Category toDomain(CategoryEntity entity) {
		return toDomain(entity, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default List<Category> toDomainList(List<CategoryEntity> entities) {
		return toDomainList(entities, new CycleAvoidingMappingContext());
	}

	@DoIgnore @Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "mapStringToImageUrl")
	@Mapping(target = "catalog", ignore = true)
	default CategoryEntity toEntity(CategoryDTO dto) {
		return toEntity(dto, new CycleAvoidingMappingContext());
	}
}
