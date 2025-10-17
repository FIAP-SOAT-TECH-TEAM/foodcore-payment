package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.StockDTO;
import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

/**
 * Mapper responsável por mapear entre a entidade de domínio Stock e seu
 * correspondente DTO.
 */
public class StockDTOMapper {

	/**
	 * Converte um StockDTO para um objeto de dominio Stock.
	 *
	 * @param dto
	 *            o StockDTO a ser convertido
	 * @return o objeto Stock correspondente
	 */
	public static Stock toDomain(StockDTO dto) {
		if (dto == null) {
			throw new IllegalArgumentException("O DTO de estoque não pode ser nulo");
		}

		Stock stock = new Stock(dto.quantity());
		stock.setId(dto.id());

		if (dto.createdAt() != null && dto.updatedAt() != null) {
			stock.setAuditInfo(new AuditInfo(dto.createdAt(), dto.updatedAt()));
		}

		return stock;
	}

	/**
	 * Converte um objeto Stock para um StockDTO.
	 *
	 * @param stock
	 *            o objeto Stock a ser convertido
	 * @return o StockDTO correspondente
	 */
	public static StockDTO toDTO(Stock stock) {
		return new StockDTO(stock.getId(), stock.getQuantity(), stock.getAuditInfo().getCreatedAt(),
				stock.getAuditInfo().getUpdatedAt());
	}
}
