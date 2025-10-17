package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.mapper.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.soat.fiap.food.core.api.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.MercadoPagoGenerateQrCodeItemEntity;

/**
 * Mapper responsável por converter objetos
 * {@link OrderCreatedInput.OrderCreatedItemInput} em
 * {@link MercadoPagoGenerateQrCodeItemEntity}, utilizados na geração de QR
 * Codes de pagamento.
 */
@Mapper(componentModel = "spring")
public interface GenerateQrCodeItemRequestMapper {

	/**
	 * Converte um {@link Long} em {@link String}, tratando valores nulos.
	 *
	 * @param id
	 *            Identificador do produto
	 * @return ID como string, ou null se o valor for nulo
	 */
	@Named("toString")
	static String longToString(Long id) {
		return id != null ? id.toString() : null;
	}

	/**
	 * Converte um {@link OrderCreatedInput.OrderCreatedItemInput} em
	 * {@link MercadoPagoGenerateQrCodeItemEntity}, preenchendo os campos
	 * necessários com base nas regras exigidas pelo integrador.
	 *
	 * @param item
	 *            Item do pedido a ser convertido
	 * @return Objeto {@link MercadoPagoGenerateQrCodeItemEntity} preenchido
	 */
	@Mapping(target = "sku_number", source = "productId", qualifiedByName = "toString")
	@Mapping(target = "category", constant = "Food") @Mapping(target = "title", source = "name")
	@Mapping(target = "description", source = "observations") @Mapping(target = "unit_price", source = "unitPrice")
	@Mapping(target = "quantity", source = "quantity") @Mapping(target = "unit_measure", constant = "unit")
	@Mapping(target = "total_amount", source = "subtotal")
	MercadoPagoGenerateQrCodeItemEntity toRequest(OrderCreatedInput.OrderCreatedItemInput item);
}
