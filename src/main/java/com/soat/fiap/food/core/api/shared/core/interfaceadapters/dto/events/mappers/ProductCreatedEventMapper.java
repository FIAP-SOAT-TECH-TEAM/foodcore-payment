package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.mappers;

import com.soat.fiap.food.core.api.catalog.core.domain.events.ProductCreatedEvent;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.ProductCreatedEventDto;

/**
 * Classe utilitária responsável por mapear {@link ProductCreatedEvent} para o
 * DTO {@link ProductCreatedEventDto}, utilizado para transporte de dados do
 * evento de produto criado.
 */
public class ProductCreatedEventMapper {

	/**
	 * Converte um {@link ProductCreatedEvent} em um {@link ProductCreatedEventDto}.
	 *
	 * @param event
	 *            Evento de criação de produto.
	 * @return DTO com os dados do produto criado.
	 */
	public static ProductCreatedEventDto toDto(ProductCreatedEvent event) {
		ProductCreatedEventDto dto = new ProductCreatedEventDto();
		dto.setProductId(event.getProductId());
		dto.setProductName(event.getProductName());
		dto.setPrice(event.getPrice());
		dto.setCategoryId(event.getCategoryId());
		dto.setTimestamp(event.getTimestamp());
		return dto;
	}
}
