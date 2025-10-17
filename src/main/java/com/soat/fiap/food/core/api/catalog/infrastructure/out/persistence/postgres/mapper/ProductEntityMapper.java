package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.*;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CategoryDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.CategoryEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.ProductEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper.shared.ImageURLMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;

/**
 * Mapper que converte entre a entidade de domínio Product e a entidade JPA
 * ProductEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {StockEntityMapper.class,
		ImageURLMapper.class})
public interface ProductEntityMapper {

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
	@Mapping(target = "category", source = "category", qualifiedByName = "mapCategory")
	ProductDTO toDTO(ProductEntity entity);

	/**
	 * Mapeia uma entidade {@link CategoryEntity} para um {@link CategoryDTO}.
	 *
	 * <p>
	 * Este método tem como finalidade evitar o carregamento completo da entidade
	 * {@link CategoryEntity} e suas associações durante o processo de mapeamento,
	 * reduzindo o acoplamento e o custo de consulta no banco de dados
	 * (LazyLoading).
	 * </p>
	 *
	 * @param category
	 *            Entidade de categoria associada ao produto
	 * @return DTO da categoria
	 */
	@Named("mapCategory")
	default CategoryDTO mapCategory(CategoryEntity category) {
		var categoryIsActive = category.getActive();
		var categoryDetails = category.getDetails();
		var categoryDisplayOrder = category.getDisplayOrder();

		return new CategoryDTO(null, categoryDetails, null, categoryDisplayOrder, categoryIsActive, null, null, null);
	}

	/**
	 * Converte uma entidade JPA para uma entidade de domínio
	 *
	 * @param entity
	 *            Entidade JPA
	 * @param cycleAvoidingMappingContext
	 *            Contexto para evitar ciclos
	 * @return Entidade de domínio
	 */
	@Mapping(target = "stock", source = "stock")
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.AuditInfoMapper.buildAuditInfo(entity.getAuditInfo().getCreatedAt(), entity.getAuditInfo().getUpdatedAt()))")
	Product toDomain(ProductEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	/**
	 * Converte uma lista de entidades JPA para uma lista de entidades de domínio
	 *
	 * @param entities
	 *            Lista de entidades JPA
	 * @param cycleAvoidingMappingContext
	 *            Contexto para evitar ciclos
	 * @return Lista de entidades de domínio
	 */
	@Mapping(target = "stock", source = "stock")
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.AuditInfoMapper.buildAuditInfo(entities.getAuditInfo().getCreatedAt(), entities.getAuditInfo().getUpdatedAt()))")
	List<Product> toDomainList(List<ProductEntity> entities,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	/**
	 * Converte uma entidade de domínio para uma entidade JPA
	 *
	 * @param dto
	 *            DTO do produto
	 * @return Entidade JPA
	 */
	@Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "mapStringToImageUrl")
	@Mapping(target = "stock", source = "stock")
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.AuditInfoMapper.buildAuditInfo(dto.createdAt(), dto.updatedAt()))")
	@Mapping(target = "category", ignore = true)
	ProductEntity toEntity(ProductDTO dto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	@DoIgnore
	default Product toDomain(ProductEntity entity) {
		return toDomain(entity, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default List<Product> toDomainList(List<ProductEntity> entities) {
		return toDomainList(entities, new CycleAvoidingMappingContext());
	}

	/**
	 * Converte uma lista de entidades JPA para uma lista de DTOs.
	 *
	 * @param entities
	 *            Lista de entidades JPA
	 * @return Lista de DTOs correspondentes
	 */
	@Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "mapImageUrlToString")
	@Mapping(source = "auditInfo.createdAt", target = "createdAt")
	@Mapping(source = "auditInfo.updatedAt", target = "updatedAt")
	List<ProductDTO> toDTOList(List<ProductEntity> entities);

	@DoIgnore
	default ProductEntity toEntity(ProductDTO dto) {
		return toEntity(dto, new CycleAvoidingMappingContext());
	}
}
