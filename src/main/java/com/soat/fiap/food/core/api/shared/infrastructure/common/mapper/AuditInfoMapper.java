package com.soat.fiap.food.core.api.shared.infrastructure.common.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

@Mapper(componentModel = "spring")
public interface AuditInfoMapper {

	@Named("buildAuditInfo")
	static AuditInfo buildAuditInfo(LocalDateTime createdAt, LocalDateTime updatedAt) {
		if (createdAt == null || updatedAt == null)
			return new AuditInfo();
		return new AuditInfo(createdAt, updatedAt);
	}

	@Named("mapCreatedAt")
	default LocalDateTime mapCreatedAt(AuditInfo auditInfo) {
		return auditInfo != null ? auditInfo.getCreatedAt() : null;
	}

	@Named("mapUpdatedAt")
	default LocalDateTime mapUpdatedAt(AuditInfo auditInfo) {
		return auditInfo != null ? auditInfo.getUpdatedAt() : null;
	}
}
