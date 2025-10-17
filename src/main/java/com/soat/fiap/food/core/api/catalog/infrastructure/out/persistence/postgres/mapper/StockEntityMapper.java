package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.StockDTO;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.StockEntity;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;

/**
 * Mapper que converte entre a entidade de domínio Stock e a entidade JPA
 * StockEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockEntityMapper {

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
	Stock toDomain(StockEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

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
	List<Stock> toDomainList(List<StockEntity> entities,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	/**
	 * Converte uma entidade de domínio para uma entidade JPA
	 *
	 * @param domain
	 *            Entidade de domínio
	 * @return Entidade JPA
	 */
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.AuditInfoMapper.buildAuditInfo(domain.getAuditInfo().getCreatedAt(), domain.getAuditInfo().getUpdatedAt()))")
	StockEntity toEntity(Stock domain, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	/**
	 * Converte uma entidade JPA para um DTO.
	 *
	 * @param entity
	 *            Entidade JPA
	 * @return DTO correspondente
	 */
	@Mapping(source = "auditInfo.createdAt", target = "createdAt")
	@Mapping(source = "auditInfo.updatedAt", target = "updatedAt")
	StockDTO toDTO(StockEntity entity);

	@DoIgnore
	default Stock toDomain(StockEntity entity) {
		return toDomain(entity, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default List<Stock> toDomainList(List<StockEntity> entities) {
		return toDomainList(entities, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default StockEntity toEntity(Stock domain) {
		return toEntity(domain, new CycleAvoidingMappingContext());
	}
}
