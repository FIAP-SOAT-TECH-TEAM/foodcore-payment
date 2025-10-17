package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import java.util.List;
import java.util.Objects;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.StockDTO;
import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

/**
 * Mapper responsável por mapear entre a entidade de domínio Product e seu
 * correspondente DTO.
 */
public class ProductDTOMapper {

	/**
	 * Converte um ProductDTO para um objeto de dominio Product.
	 *
	 * @param dto
	 *            o ProductDTO a ser convertido
	 * @return o objeto Product correspondente
	 */
	public static Product toDomain(ProductDTO dto) {
		Objects.requireNonNull(dto, "O DTO do produto não pode ser nulo");

		ImageUrl imageUrl = dto.imageUrl() != null ? new ImageUrl(dto.imageUrl()) : null;

		Product product = new Product(dto.details(), dto.price(), imageUrl, dto.displayOrder());
		product.setId(dto.id());

		if (dto.category() != null) {
			var category = CategoryDTOMapper.toDomain(dto.category());
			product.setCategory(category);
		}

		if (dto.stock() != null) {
			var stock = StockDTOMapper.toDomain(dto.stock());
			product.setStock(stock);
		} else {
			product.setStockQuantity(0);
		}
		product.setActive(dto.active());

		if (dto.createdAt() != null && dto.updatedAt() != null) {
			product.setAuditInfo(new AuditInfo(dto.createdAt(), dto.updatedAt()));
		}

		return product;
	}

	/**
	 * Converte um objeto Product para um ProductDTO.
	 *
	 * @param product
	 *            o objeto Product a ser convertido
	 * @return o ProductDTO correspondente
	 */
	public static ProductDTO toDTO(Product product) {
		StockDTO stockDTO = StockDTOMapper.toDTO(product.getStock());
		return new ProductDTO(product.getId(), product.getDetails(), product.getImageUrlValue(), product.getPrice(),
				stockDTO, null, product.getDisplayOrder(), product.isActive(), product.getCreatedAt(),
				product.getUpdatedAt());
	}

	/**
	 * Converte uma lista de ProductDTO para uma lista de objetos Product.
	 *
	 * @param dtoList
	 *            a lista de ProductDTO a ser convertida
	 * @return uma lista de objetos Product correspondentes
	 */
	public static List<Product> toDomainList(List<ProductDTO> dtoList) {
		Objects.requireNonNull(dtoList, "A lista de DTOs de produtos não pode ser nula");

		return dtoList.stream().filter(Objects::nonNull).map(ProductDTOMapper::toDomain).toList();
	}
}
