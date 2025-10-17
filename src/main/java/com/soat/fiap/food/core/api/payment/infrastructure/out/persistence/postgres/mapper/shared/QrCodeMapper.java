package com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.mapper.shared;

import org.mapstruct.Mapper;

import com.soat.fiap.food.core.api.payment.core.domain.vo.QrCode;

/**
 * Mapper que converte value object QrCode para string e vice-versa
 */
@Mapper(componentModel = "spring")
public interface QrCodeMapper {

	default String toString(QrCode qrCode) {
		return qrCode != null ? qrCode.value() : null;
	}

	default QrCode toQrCode(String code) {
		return code != null ? new QrCode(code) : null;
	}
}
