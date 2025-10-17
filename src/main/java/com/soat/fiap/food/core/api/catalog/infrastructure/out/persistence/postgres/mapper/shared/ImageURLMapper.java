package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper.shared;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;

@Mapper(componentModel = "spring")
public interface ImageURLMapper {

	@Named("mapImageUrlToString")
	default String mapImageUrlToString(ImageUrl value) {
		return value != null ? value.imageUrl() : null;
	}

	@Named("mapStringToImageUrl")
	default ImageUrl mapStringToImageUrl(String value) {
		return value != null ? new ImageUrl(value) : null;
	}
}
