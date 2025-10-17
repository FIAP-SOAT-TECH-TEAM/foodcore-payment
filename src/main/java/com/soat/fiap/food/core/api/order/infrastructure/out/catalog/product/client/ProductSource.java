package com.soat.fiap.food.core.api.order.infrastructure.out.catalog.product.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.product.ProductDTO;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.ProductDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.out.catalog.product.exceptions.ProductException;
import com.soat.fiap.food.core.api.order.infrastructure.out.catalog.product.mapper.response.ProductDTOMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação concreta: DataSource para comunicação com o microsserviço de
 * Catalog (Product)
 */
@Component @Slf4j @RequiredArgsConstructor
public class ProductSource implements ProductDataSource {

	@Autowired
	private ProductClient client;

	@Autowired
	private ProductDTOMapper productDTOMapper;

	@Override
	public List<ProductDTO> findByProductIds(List<Long> productIds) {
		try {
			var response = client.getProductsByIds(productIds);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				var productEntities = response.getBody();
				return productDTOMapper.toDtoList(productEntities);
			} else {
				String errorMsg = "Erro do microsserviço de catálogo (Product) | Status code: "
						+ response.getStatusCode().value();
				log.warn(errorMsg);
				throw new ProductException(errorMsg, null, response.getStatusCode().value());
			}
		} catch (ProductException e) {
			throw e;
		} catch (Exception e) {
			log.error("Erro inesperado ao contatar a API do microsserviço de catálogo (Product)", e);
			throw new ProductException("Erro inesperado ao chamar API do microsserviço de catálogo (Product)", e, 500);
		}
	}
}
