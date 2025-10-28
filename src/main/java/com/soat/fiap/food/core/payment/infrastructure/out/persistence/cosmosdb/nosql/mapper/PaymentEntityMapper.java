package com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.PaymentDTO;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity.PaymentEntity;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.mapper.shared.QrCodeMapper;
import com.soat.fiap.food.core.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.shared.infrastructure.common.mapper.DoIgnore;

/**
 * Mapper que converte entre a entidade de domínio Payment e a entidade JPA
 * PaymentEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = QrCodeMapper.class)
public interface PaymentEntityMapper {
	PaymentEntity toEntity(PaymentDTO domain, @Context CycleAvoidingMappingContext context);

	@Mapping(target = "createdAt", source = "auditInfo.createdAt")
	@Mapping(target = "updatedAt", source = "auditInfo.updatedAt")
	PaymentDTO toDTO(PaymentEntity entity);

	@DoIgnore
	default PaymentEntity toEntity(PaymentDTO dto) {
		return toEntity(dto, new CycleAvoidingMappingContext());
	}
}
