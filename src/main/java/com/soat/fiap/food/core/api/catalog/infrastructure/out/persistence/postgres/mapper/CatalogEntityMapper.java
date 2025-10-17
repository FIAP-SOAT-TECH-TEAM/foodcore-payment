package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CatalogDTO;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.CatalogEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper.shared.ImageURLMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.AuditInfoMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;

/**
 * Mapper que converte entre a entidade JPA CatalogEntity e o DTO CatalogDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ImageURLMapper.class,
		CategoryEntityMapper.class, AuditInfoMapper.class})
public interface CatalogEntityMapper {

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
	Catalog toDomain(CatalogEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

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
	List<Catalog> toDomainList(List<CatalogEntity> entities,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	/**
	 * Converte uma entidade JPA para um DTO.
	 *
	 * @param entity
	 *            Entidade JPA
	 * @return DTO correspondente
	 */
	@Mapping(source = "auditInfo.createdAt", target = "createdAt")
	@Mapping(source = "auditInfo.updatedAt", target = "updatedAt")
	CatalogDTO toDTO(CatalogEntity entity);

	/**
	 * Converte uma lista de entidades JPA para uma lista de DTOs.
	 *
	 * @param entities
	 *            Lista de entidades JPA
	 * @return Lista de DTOs
	 */
	@Mapping(source = "auditInfo.createdAt", target = "createdAt")
	@Mapping(source = "auditInfo.updatedAt", target = "updatedAt")
	List<CatalogDTO> toDTOList(List<CatalogEntity> entities);

	/**
	 * Converte um DTO para uma entidade JPA.
	 *
	 * @param dto
	 *            DTO
	 * @return Entidade JPA correspondente
	 */
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.AuditInfoMapper.buildAuditInfo(dto.createdAt(), dto.updatedAt()))")
	CatalogEntity toEntity(CatalogDTO dto, @Context CycleAvoidingMappingContext context);

	@DoIgnore
	default Catalog toDomain(CatalogEntity entity) {
		return toDomain(entity, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default List<Catalog> toDomainList(List<CatalogEntity> entities) {
		return toDomainList(entities, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default CatalogEntity toEntity(CatalogDTO dto) {
		return toEntity(dto, new CycleAvoidingMappingContext());
	}
}
